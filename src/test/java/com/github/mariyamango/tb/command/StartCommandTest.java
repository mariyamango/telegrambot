package com.github.mariyamango.tb.command;

import org.junit.jupiter.api.DisplayName;

import static com.github.mariyamango.tb.command.CommandName.START;
import static com.github.mariyamango.tb.command.StartCommand.START_MESSAGE;

@DisplayName("Unit-level testing for StartCommand")
public class StartCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return START.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return START_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StartCommand(sendBotMessageService, telegramUserService);
    }
}
