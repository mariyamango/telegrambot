package com.github.mariyamango.tb.command;

import com.github.mariyamango.tb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Start {@link Command}.
 */

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final static String START_MESSAGE = "Hello. I am Javarush Telegram Bot. I will help you keep up to date with the latest articles "
            + "by authors that interest you. I'm still small and just learning.";

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute (Update update){
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}