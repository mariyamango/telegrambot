package com.github.mariyamango.tb.command;

import com.github.mariyamango.tb.repository.entity.GroupSub;
import com.github.mariyamango.tb.repository.entity.TelegramUser;
import com.github.mariyamango.tb.service.SendBotMessageService;
import com.github.mariyamango.tb.service.TelegramUserService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;

import java.util.stream.Collectors;

import static com.github.mariyamango.tb.command.CommandUtils.getChatId;

/**
 * {@link Command} for getting list of {@link GroupSub}.
 */

public class ListGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public ListGroupSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update)  {
        //TODO add exception handling
        TelegramUser telegramUser = telegramUserService.findByChatId(getChatId(update))
                .orElseThrow(NotFoundException::new);
        String message;
        if(CollectionUtils.isEmpty(telegramUser.getGroupSubs())) {
            message = "There are no group subscriptions yet. To add a subscription type /addgroupsub";
        } else {
            String collectedGroups = telegramUser.getGroupSubs().stream()
                    .map(it -> "Группа: " + it.getTitle() + " , ID = " + it.getId() + " \n")
                    .collect(Collectors.joining());
            message =  String.format("I found all the group subscriptions: \n\n %s", collectedGroups);
        }


        sendBotMessageService.sendMessage(telegramUser.getChatId(), message);
    }
}
