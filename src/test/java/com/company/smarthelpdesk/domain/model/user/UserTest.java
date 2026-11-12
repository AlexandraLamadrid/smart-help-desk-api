package com.company.smarthelpdesk.domain.model.user;

import com.company.smarthelpdesk.domain.valueobject.EmailAddress;

import com.company.smarthelpdesk.domain.valueobject.FullName;

import com.company.smarthelpdesk.domain.valueobject.UserId;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User createUser() {

        return new User(

                new UserId(UUID.randomUUID()),

                new FullName("Ana Maria Lopez"),

                new EmailAddress("ana@gmail.com")

        );

    }

    @Test

    void shouldNotDuplicateRoles() {

        User user = createUser();

        user.assignRole(RoleName.ADMIN);

        user.assignRole(RoleName.ADMIN);

        assertEquals(1, user.getRoles().size());

    }

    @Test

    void shouldRemoveRole() {

        User user = createUser();

        user.assignRole(RoleName.TECHNICIAN);

        user.removeRole(RoleName.TECHNICIAN);

        assertFalse(

                user.getRoles().contains(RoleName.TECHNICIAN)

        );

    }

    @Test

    void shouldDeactivateUser() {

        User user = createUser();

        user.deactivate();

        assertFalse(user.isActive());

    }

    @Test

    void shouldReactivateUser() {

        User user = createUser();

        user.deactivate();

        user.activate();

        assertTrue(user.isActive());

    }

    @Test

    void shouldPreventExternalRoleModification() {

        User user = createUser();

        assertThrows(

                UnsupportedOperationException.class,

                () -> user.getRoles().add(RoleName.ADMIN)

        );

    }

}
