package pl.wsb.fitnesstracker.user.api;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param user The user to be created
     * @return The created user
     */
    User createUser(User user);

    /**
     * Updates existing user. The provided user must contain valid ID.
     *
     * @param user user with updated data
     * @return updated user
     */
    User updateUser(User user);

    /**
     * Deletes user by id
     *
     * @param userId id of user to delete
     */
    void deleteUser(Long userId);

}
