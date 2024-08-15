package com.github.mariyamango.tb.command;

import com.github.mariyamango.tb.repository.entity.TelegramUser;
import com.github.mariyamango.tb.service.SendBotMessageService;
import com.github.mariyamango.tb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.mariyamango.tb.command.CommandUtils.getChatId;

/**
 * Start {@link Command}.
 */

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String START_MESSAGE = "Hello. I am Javarush Telegram Bot.\n " +
            "I will help you keep up to date with the latest articles by authors that interest you.\n\n" +
            "Click /addgroupsub to subscribe to a group of articles in JavaRush.\n" +
            "Don't know what I mean? Write /help to find out what I can do.";

    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute (Update update){
        Long chatId = getChatId(update);

        telegramUserService.findByChatId(chatId).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    telegramUserService.save(user);
                },
                () -> {
                    TelegramUser telegramUser = new TelegramUser();
                    telegramUser.setActive(true);
                    telegramUser.setChatId(chatId);
                    telegramUserService.save(telegramUser);
                });

        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}