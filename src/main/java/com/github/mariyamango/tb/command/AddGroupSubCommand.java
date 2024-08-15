package com.github.mariyamango.tb.command;

import com.github.mariyamango.tb.javarushclient.JavaRushGroupClient;
import com.github.mariyamango.tb.javarushclient.dto.GroupDiscussionInfo;
import com.github.mariyamango.tb.javarushclient.dto.GroupRequestArgs;
import com.github.mariyamango.tb.repository.entity.GroupSub;
import com.github.mariyamango.tb.service.GroupSubService;
import com.github.mariyamango.tb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.mariyamango.tb.command.CommandName.ADD_GROUP_SUB;
import static com.github.mariyamango.tb.command.CommandUtils.getChatId;
import static com.github.mariyamango.tb.command.CommandUtils.getMessage;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Add Group subscription {@link Command}.
 */
//TODO add unit test for the command logic

public class AddGroupSubCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final JavaRushGroupClient javaRushGroupClient;
    private final GroupSubService groupSubService;

    public AddGroupSubCommand(SendBotMessageService sendBotMessageService, JavaRushGroupClient javaRushGroupClient, GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.javaRushGroupClient = javaRushGroupClient;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())){
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        Long chatId = getChatId(update);
        if (isNumeric(groupId)){
            GroupDiscussionInfo groupById = javaRushGroupClient.getGroupById(Integer.parseInt(groupId));
            if (isNull(groupById.getId())) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub savedGroupSub = groupSubService.save(chatId, groupById);
            sendBotMessageService.sendMessage(chatId,"Subscribed you to the group " + savedGroupSub.getTitle());
        } else {
            sendNotValidGroupID(chatId, groupId);
        }
    }

    private void sendNotValidGroupID(Long chatId, String groupId) {
        String groupNotFoundMessage = "Group ID is incorrect = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }

    private void sendGroupNotFound(Long chatId, String groupId) {
        String groupNotFoundMessage = "There is no group with ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }

    private void sendGroupIdList(Long chatId) {
        String groupIds = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build()).stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = "To subscribe to a group, pass the command along with the group ID. \n" +
                "For example: /addgroupsub 30 \n\n" +
                "I have prepared a list of all groups - choose which one you want :) \n\n" +
                "group name - group ID \n\n" +
                "%s";
        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }
}
