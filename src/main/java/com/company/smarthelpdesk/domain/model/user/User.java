package com.company.smarthelpdesk.domain.model.user;

import com.company.smarthelpdesk.domain.exception.InvalidValueException;

import com.company.smarthelpdesk.domain.valueobject.EmailAddress;

import com.company.smarthelpdesk.domain.valueobject.FullName;

import com.company.smarthelpdesk.domain.valueobject.UserId;

import java.util.Collections;

import java.util.HashSet;

import java.util.Set;

public final class User {

    private final UserId id;

    private final FullName fullName;

    private final EmailAddress email;

    private final Set<RoleName> roles;

    private boolean active;

    public User(

            UserId id,

            FullName fullName,

            EmailAddress email

    ) {

        if (id == null || fullName == null || email == null) {

            throw new InvalidValueException(

                    "User required fields cannot be null"

            );

        }
        this.id = id;

        this.fullName = fullName;

        this.email = email;

        this.roles = new HashSet<>();

        this.active = true;

    }

    public void assignRole(RoleName role) {

        if (role == null) {

            throw new InvalidValueException(

                    "Role is required"

            );

        }

        roles.add(role);

    }

    public void removeRole(RoleName role) {

        if (role == null) {

            throw new InvalidValueException(

                    "Role is required"

            );

        }

        roles.remove(role);

    }

    public void activate() {

        this.active = true;

    }

    public void deactivate() {

        this.active = false;

    }

    public UserId getId() {

        return id;

    }

    public FullName getFullName() {

        return fullName;

    }

    public EmailAddress getEmail() {

        return email;

    }

    public Set<RoleName> getRoles() {

        return Collections.unmodifiableSet(roles);

    }

    public boolean isActive() {

        return active;

    }

}