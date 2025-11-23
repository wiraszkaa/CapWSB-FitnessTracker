package pl.wsb.fitnesstracker.training.internal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.wsb.fitnesstracker.training.api.Training;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Training entities.
 * Uses EntityManager for custom JPQL queries.
 */
@Repository
public class TrainingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Find all trainings for a specific user.
     *
     * @param userId the user ID
     * @return list of trainings for the user
     */
    public List<Training> findByUserId(Long userId) {
        TypedQuery<Training> query = entityManager.createQuery(
                "SELECT t FROM Training t WHERE t.user.id = :userId ORDER BY t.startTime DESC",
                Training.class
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    /**
     * Find all trainings by activity type.
     *
     * @param activityType the activity type
     * @return list of trainings with the specified activity type
     */
    public List<Training> findByActivityType(ActivityType activityType) {
        TypedQuery<Training> query = entityManager.createQuery(
                "SELECT t FROM Training t WHERE t.activityType = :activityType",
                Training.class
        );
        query.setParameter("activityType", activityType);
        return query.getResultList();
    }

    /**
     * Find trainings within a date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of trainings within the date range
     */
    public List<Training> findByDateRange(Date startDate, Date endDate) {
        TypedQuery<Training> query = entityManager.createQuery(
                "SELECT t FROM Training t WHERE t.startTime BETWEEN :startDate AND :endDate ORDER BY t.startTime",
                Training.class
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    /**
     * Find trainings by user and activity type.
     *
     * @param userId the user ID
     * @param activityType the activity type
     * @return list of trainings matching the criteria
     */
    public List<Training> findByUserIdAndActivityType(Long userId, ActivityType activityType) {
        TypedQuery<Training> query = entityManager.createQuery(
                "SELECT t FROM Training t WHERE t.user.id = :userId AND t.activityType = :activityType",
                Training.class
        );
        query.setParameter("userId", userId);
        query.setParameter("activityType", activityType);
        return query.getResultList();
    }

    /**
     * Find training by ID.
     *
     * @param id the training ID
     * @return optional containing the training if found
     */
    public Optional<Training> findById(Long id) {
        Training training = entityManager.find(Training.class, id);
        return Optional.ofNullable(training);
    }

    /**
     * Save a new training.
     *
     * @param training the training to save
     * @return the saved training
     */
    public Training save(Training training) {
        if (training.getId() == null) {
            entityManager.persist(training);
            return training;
        } else {
            return entityManager.merge(training);
        }
    }

    /**
     * Delete a training.
     *
     * @param training the training to delete
     */
    public void delete(Training training) {
        if (entityManager.contains(training)) {
            entityManager.remove(training);
        } else {
            entityManager.remove(entityManager.merge(training));
        }
    }

    /**
     * Get total distance covered by a user.
     * Uses native SQL query.
     *
     * @param userId the user ID
     * @return total distance
     */
    public Double getTotalDistanceByUser(Long userId) {
        String sql = "SELECT SUM(distance) FROM trainings WHERE user_id = :userId";
        Double result = (Double) entityManager.createNativeQuery(sql)
                .setParameter("userId", userId)
                .getSingleResult();
        return result != null ? result : 0.0;
    }

    /**
     * Count trainings by user.
     *
     * @param userId the user ID
     * @return number of trainings
     */
    public Long countByUserId(Long userId) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(t) FROM Training t WHERE t.user.id = :userId",
                Long.class
        );
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }

    /**
     * Find all trainings.
     *
     * @return list of all trainings
     */
    public List<Training> findAll() {
        TypedQuery<Training> query = entityManager.createQuery(
                "SELECT t FROM Training t ORDER BY t.startTime DESC",
                Training.class
        );
        return query.getResultList();
    }
}
