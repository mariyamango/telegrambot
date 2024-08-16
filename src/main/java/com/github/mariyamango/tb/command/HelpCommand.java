package com.github.mariyamango.tb.command;

import com.github.mariyamango.tb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.mariyamango.tb.command.CommandName.*;
import static com.github.mariyamango.tb.command.CommandUtils.getChatId;

/**
 * Help {@link Command}.
 */

public class HelpCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("✨<b>Available commands</b>✨ \n\n"
            + "<b>Start\\stop working with the bot:</b>\n"
            + "%s - get started with me\n"
            + "%s - stop working with me\n\n"

            + "Working with group subscriptions:\n"
            + "%s - subscribe to a group of articles\n"
            + "%s - unsubscribe from a group of articles\n"
            + "%s - get a list of groups you are subscribed to\n\n"

            + "%s - get statistics\n"
            + "%s - get help by working with me\n",
            START.getCommandName(), STOP.getCommandName(), ADD_GROUP_SUB.getCommandName(), DELETE_GROUP_SUB.getCommandName(),
            LIST_GROUP_SUB.getCommandName(), STAT.getCommandName(), HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), HELP_MESSAGE);
    }
}
