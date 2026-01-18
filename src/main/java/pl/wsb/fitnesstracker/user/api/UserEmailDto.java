package pl.wsb.fitnesstracker.user.api;

/**
 * Representation returned when searching by email fragment: id and email
 */
public record UserEmailDto(Long id, String email) {

}
