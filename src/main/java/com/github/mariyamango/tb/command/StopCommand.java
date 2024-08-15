package com.github.mariyamango.tb.command;

import com.github.mariyamango.tb.service.SendBotMessageService;
import com.github.mariyamango.tb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.mariyamango.tb.command.CommandUtils.getChatId;

/**
 * Stop {@link Command}.
 */

public class StopCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public static final String STOP_MESSAGE = "Deactivated all your subscriptions \uD83D\uDE1F.";

    public StopCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), STOP_MESSAGE);
        telegramUserService.findByChatId(getChatId(update))
                .ifPresent(it -> {
                    it.setActive(false);
                    telegramUserService.save(it);
                });
    }
}
