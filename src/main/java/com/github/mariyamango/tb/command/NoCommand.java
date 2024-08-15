package com.github.mariyamango.tb.command;

import com.github.mariyamango.tb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.mariyamango.tb.command.CommandUtils.getChatId;

/**
 * No {@link Command}.
 */

public class NoCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public static final String NO_MESSAGE = "I support commands starting with the symbol '/'.\n"
            + "To see a list of commands, enter /help";

    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), NO_MESSAGE);
    }
}
