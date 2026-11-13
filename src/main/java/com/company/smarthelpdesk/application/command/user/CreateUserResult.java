package com.company.smarthelpdesk.application.command.user;
import java.util.UUID;
public record CreateUserResult(
        UUID id,
        String fullName,
        String email
) {
}