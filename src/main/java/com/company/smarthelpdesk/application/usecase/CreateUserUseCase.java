package com.company.smarthelpdesk.application.usecase;

import com.company.smarthelpdesk.application.command.user.CreateUserCommand;

import com.company.smarthelpdesk.application.command.user.CreateUserResult;

public interface CreateUserUseCase {

    CreateUserResult execute(CreateUserCommand command);

}
