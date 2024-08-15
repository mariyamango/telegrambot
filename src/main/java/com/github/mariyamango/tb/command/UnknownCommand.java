package com.github.mariyamango.tb.command;

import com.github.mariyamango.tb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.mariyamango.tb.command.CommandUtils.getChatId;

/**
 * Unknown {@link Command}.
 */

public class UnknownCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public static final String UNKNOWN_MESSAGE = "I do not understand you \uD83D\uDE1F, enter /help to find out what I understand.";

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), UNKNOWN_MESSAGE);
    }
}
