package com.github.mariyamango.tb.service;

import com.github.mariyamango.tb.bot.JavaRushTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Implementation of {@link SendBotMessageService} interface.
 */

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final JavaRushTelegramBot jrBot;

    @Autowired
    public SendBotMessageServiceImpl(JavaRushTelegramBot jrBot) {
        this.jrBot = jrBot;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        if (isBlank(message)) return;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            jrBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            // add logging to the project
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Long chatId, List<String> messages) {
        if (isEmpty(messages)) return;

        messages.forEach(m -> sendMessage(chatId, m));
    }
}
