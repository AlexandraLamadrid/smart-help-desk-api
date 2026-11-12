package com.company.smarthelpdesk.infrastructure.adapters.persistence.memory;
import com.company.smarthelpdesk.domain.model.user.User;
import com.company.smarthelpdesk.domain.port.out.UserRepositoryPort;
import com.company.smarthelpdesk.domain.valueobject.EmailAddress;
import com.company.smarthelpdesk.domain.valueobject.UserId;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
public final class InMemoryUserRepositoryAdapter
        implements UserRepositoryPort {
    private final ConcurrentMap<UserId, User> users =
            new ConcurrentHashMap<>();
    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException(
                    "User is required"
            );
        }
        users.put(user.getId(), user);
        return user;
    }
    @Override
    public Optional<User> findById(UserId id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(id));
    }
    @Override
    public Optional<User> findByEmail(EmailAddress email) {
        if (email == null) {
            return Optional.empty();
        }
        return users.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
    @Override
    public boolean existsByEmail(EmailAddress email) {
        return findByEmail(email).isPresent();
    }
}