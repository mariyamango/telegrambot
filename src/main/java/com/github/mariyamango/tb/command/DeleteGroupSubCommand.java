package com.github.mariyamango.tb.command;

import com.github.mariyamango.tb.repository.entity.GroupSub;
import com.github.mariyamango.tb.repository.entity.TelegramUser;
import com.github.mariyamango.tb.service.GroupSubService;
import com.github.mariyamango.tb.service.SendBotMessageService;
import com.github.mariyamango.tb.service.TelegramUserService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.mariyamango.tb.command.CommandName.DELETE_GROUP_SUB;
import static com.github.mariyamango.tb.command.CommandUtils.getChatId;
import static com.github.mariyamango.tb.command.CommandUtils.getMessage;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Delete Group subscription {@link Command}.
 */

public class DeleteGroupSubCommand implements Command{

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final GroupSubService groupSubService;

    public DeleteGroupSubCommand (SendBotMessageService sendBotMessageService, GroupSubService groupSubService,
                                  TelegramUserService telegramUserService){
        this.sendBotMessageService = sendBotMessageService;
        this.groupSubService = groupSubService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(DELETE_GROUP_SUB.getCommandName())){
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        Long chatId = getChatId(update);
        if (isNumeric(groupId)) {
            Optional<GroupSub> optionalGroupSub = groupSubService.findById(Integer.valueOf(groupId));
            if (optionalGroupSub.isPresent()) {
                GroupSub groupSub = optionalGroupSub.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
                groupSub.getUsers().remove(telegramUser);
                groupSubService.save(groupSub);
                sendBotMessageService.sendMessage(chatId, format("Removed a subscription to a group:  %s", groupSub.getTitle()));
            } else {
                sendBotMessageService.sendMessage(chatId, "I didn't find such a group =/");
            }
        } else {
            sendBotMessageService.sendMessage(chatId,"Incorrect group ID format.\n " +
                    "ID must be a positive integer");
        }
    }

    private void sendGroupIdList(Long chatId) {
        String message;
        List<GroupSub> groupSubs = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new)
                .getGroupSubs();
        if (CollectionUtils.isEmpty(groupSubs)){
            message = "There are no group subscriptions yet. To add a subscription write /addgroupsub";
        } else {
         String userGroupSubData = groupSubs.stream()
                 .map(group -> format("%s - %s \n", group.getTitle(), group.getId()))
                 .collect(Collectors.joining());
         message = String.format("To delete a subscription to a group, pass the command along with the group ID.\n" +
                 "For example: /deletegroupsub 16 \n\n" +
                 "I have prepared a list of all the groups you are subscribed to) \n\n" +
                 "group name - group ID \n\n" +
                 "%s", userGroupSubData);
        }
        sendBotMessageService.sendMessage(chatId, message);
    }
}
