package com.company.smarthelpdesk.domain.port.out;

import com.company.smarthelpdesk.domain.model.user.User;

import com.company.smarthelpdesk.domain.valueobject.UserId;

import com.company.smarthelpdesk.domain.valueobject.EmailAddress;

import java.util.Optional;

public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findById(UserId id);

    Optional<User> findByEmail(EmailAddress email);

    boolean existsByEmail(EmailAddress email);

}
