package pl.wsb.fitnesstracker.event;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Event entities. Uses EntityManager for custom JPQL
 * queries.
 */
@Repository
public class EventRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Find all events.
     *
     * @return list of all events
     */
    public List<Event> findAll() {
        TypedQuery<Event> query = entityManager.createQuery(
                "SELECT e FROM Event e ORDER BY e.startTime",
                Event.class
        );
        return query.getResultList();
    }

    /**
     * Find event by ID.
     *
     * @param id the event ID
     * @return optional containing the event if found
     */
    public Optional<Event> findById(Long id) {
        Event event = entityManager.find(Event.class, id);
        return Optional.ofNullable(event);
    }

    /**
     * Find events by city.
     *
     * @param city the city name
     * @return list of events in the specified city
     */
    public List<Event> findByCity(String city) {
        TypedQuery<Event> query = entityManager.createQuery(
                "SELECT e FROM Event e WHERE e.city = :city ORDER BY e.startTime",
                Event.class
        );
        query.setParameter("city", city);
        return query.getResultList();
    }

    /**
     * Find events by country.
     *
     * @param country the country name
     * @return list of events in the specified country
     */
    public List<Event> findByCountry(String country) {
        TypedQuery<Event> query = entityManager.createQuery(
                "SELECT e FROM Event e WHERE e.country = :country ORDER BY e.startTime",
                Event.class
        );
        query.setParameter("country", country);
        return query.getResultList();
    }

    /**
     * Find upcoming events (events that haven't started yet).
     *
     * @param currentDate the current date
     * @return list of upcoming events
     */
    public List<Event> findUpcomingEvents(Date currentDate) {
        TypedQuery<Event> query = entityManager.createQuery(
                "SELECT e FROM Event e WHERE e.startTime > :currentDate ORDER BY e.startTime",
                Event.class
        );
        query.setParameter("currentDate", currentDate);
        return query.getResultList();
    }

    /**
     * Find events by date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of events within the date range
     */
    public List<Event> findByDateRange(Date startDate, Date endDate) {
        TypedQuery<Event> query = entityManager.createQuery(
                "SELECT e FROM Event e WHERE e.startTime >= :startDate AND e.endTime <= :endDate ORDER BY e.startTime",
                Event.class
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    /**
     * Save a new event.
     *
     * @param event the event to save
     * @return the saved event
     */
    public Event save(Event event) {
        if (event.getId() == null) {
            entityManager.persist(event);
            return event;
        } else {
            return entityManager.merge(event);
        }
    }

    /**
     * Delete an event.
     *
     * @param event the event to delete
     */
    public void delete(Event event) {
        if (entityManager.contains(event)) {
            entityManager.remove(event);
        } else {
            entityManager.remove(entityManager.merge(event));
        }
    }

    /**
     * Count total number of events in a city using native SQL.
     *
     * @param city the city name
     * @return number of events
     */
    public Long countEventsByCity(String city) {
        String sql = "SELECT COUNT(*) FROM event WHERE city = :city";
        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter("city", city)
                .getSingleResult()).longValue();
    }

    /**
     * Find events with name containing the search term.
     *
     * @param searchTerm the search term
     * @return list of matching events
     */
    public List<Event> searchByName(String searchTerm) {
        TypedQuery<Event> query = entityManager.createQuery(
                "SELECT e FROM Event e WHERE LOWER(e.name) LIKE LOWER(:searchTerm) ORDER BY e.startTime",
                Event.class
        );
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        return query.getResultList();
    }
}


 

 

 

 