package com.github.mariyamango.tb.command;

import org.junit.jupiter.api.DisplayName;

import static com.github.mariyamango.tb.command.CommandName.STAT;
import static com.github.mariyamango.tb.command.StatCommand.STAT_MESSAGE;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE);
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}
