package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Objects;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     * Finds users whose email contains the provided fragment (case-insensitive)
     */
    default java.util.List<User> findByEmailContainingIgnoreCase(String fragment) {
        if (fragment == null) {
            return findAll();
        }
        String lower = fragment.toLowerCase();
        return findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().toLowerCase().contains(lower))
                .toList();
    }

    /**
     * Finds users older than given age (years)
     */
    default java.util.List<User> findUsersOlderThan(int age) {
        java.time.LocalDate threshold = java.time.LocalDate.now().minusYears(age);
        return findAll().stream()
                .filter(u -> u.getBirthdate() != null && u.getBirthdate().isBefore(threshold))
                .toList();
    }

}
