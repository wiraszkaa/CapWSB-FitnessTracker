package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserNameDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    UserNameDto toNameDto(User user) {
        String name = "%s %s".formatted(user.getFirstName(), user.getLastName());
        return new UserNameDto(user.getId(), name);
    }

    UserEmailDto toEmailDto(User user) {
        return new UserEmailDto(user.getId(), user.getEmail());
    }

    User toEntity(UserDto dto) {
        return new User(dto.firstName(), dto.lastName(), dto.birthdate(), dto.email());
    }
}
