package com.company.smarthelpdesk.application.command.user;
public record CreateUserCommand(
        String fullName,
        String email
) {
}