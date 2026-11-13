package com.company.smarthelpdesk.application.usecase;
import com.company.smarthelpdesk.application.command.user.CreateUserCommand;
import com.company.smarthelpdesk.application.command.user.CreateUserResult;
import com.company.smarthelpdesk.application.exception.ConflictException;
import com.company.smarthelpdesk.domain.model.user.RoleName;
import com.company.smarthelpdesk.domain.model.user.User;
import com.company.smarthelpdesk.domain.port.out.UserRepositoryPort;
import com.company.smarthelpdesk.domain.valueobject.EmailAddress;
import com.company.smarthelpdesk.domain.valueobject.FullName;
import com.company.smarthelpdesk.domain.valueobject.UserId;
import java.util.Objects;
import java.util.UUID;

public class CreateUserService implements CreateUserUseCase {
    private final UserRepositoryPort userRepository;
    public CreateUserService(UserRepositoryPort userRepository) {
        this.userRepository =
                Objects.requireNonNull(userRepository);
    }
    @Override
    public CreateUserResult execute(CreateUserCommand command) {
        Objects.requireNonNull(command, "Command is required");
        FullName fullName = new FullName(command.fullName());
        EmailAddress email = new EmailAddress(command.email());
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(
                    "A user with this email already exists"
            );
        }
        User user = new User(

                new UserId(UUID.randomUUID()),

                fullName,

                email

        );

        user.assignRole(RoleName.USER);

        User savedUser = userRepository.save(user);

        return new CreateUserResult(

                savedUser.getId().value(),

                savedUser.getFullName().value(),

                savedUser.getEmail().value()

        );
    }

}