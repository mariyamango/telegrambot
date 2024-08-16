package com.github.mariyamango.tb.command;

import com.github.mariyamango.tb.repository.entity.GroupSub;
import com.github.mariyamango.tb.repository.entity.TelegramUser;
import com.github.mariyamango.tb.service.GroupSubService;
import com.github.mariyamango.tb.service.SendBotMessageService;
import com.github.mariyamango.tb.service.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static com.github.mariyamango.tb.command.AbstractCommandTest.prepareUpdate;
import static com.github.mariyamango.tb.command.CommandName.DELETE_GROUP_SUB;

@DisplayName("Unit-level testing for DeleteGroupSubCommand")
public class DeleteGroupSubCommandTest {
    private Command command;
    private SendBotMessageService sendBotMessageService;
    GroupSubService groupSubService;
    TelegramUserService telegramUserService;

    @BeforeEach
    public void init(){
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        groupSubService = Mockito.mock(GroupSubService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);

        command = new DeleteGroupSubCommand(sendBotMessageService, groupSubService, telegramUserService);
    }

    @Test
    public void shouldProperlyReturnEmptySubscriptionList(){
        //given
        Long chatId = 23456L;
        Update update = prepareUpdate(chatId, DELETE_GROUP_SUB.getCommandName());
        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(new TelegramUser()));
        String expectedMessage = "There are no group subscriptions yet. To add a subscription write /addgroupsub";
        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    public void shouldProperlyReturnSubscriptionList(){
        //given
        Long chatId = 23456L;
        Update update = prepareUpdate(chatId, DELETE_GROUP_SUB.getCommandName());
        TelegramUser telegramUser = new TelegramUser();
        GroupSub gs1 = new GroupSub();
        gs1.setId(123);
        gs1.setTitle("GS1 Title");
        telegramUser.setGroupSubs(Collections.singletonList(gs1));
        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(telegramUser));
        String expectedMessage = "To delete a subscription to a group, pass the command along with the group ID.\n" +
                 "For example: /deletegroupsub 16 \n\n" +
                 "I have prepared a list of all the groups you are subscribed to) \n\n" +
                 "group name - group ID \n\n" +
                 "GS1 Title - 123 \n";
        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    public void shouldRejectByInvalidGroupId(){
        //given
        Long chatId = 23456L;
        Update update = prepareUpdate(chatId, DELETE_GROUP_SUB.getCommandName());
        TelegramUser telegramUser = new TelegramUser();
        GroupSub gs1 = new GroupSub();
        gs1.setId(123);
        gs1.setTitle("GS1 Title");
        telegramUser.setGroupSubs(Collections.singletonList(gs1));
        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(telegramUser));
        String expectedMessage = "Incorrect group ID format.\n " +
                "ID must be a positive integer";
        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    public void shouldProperlyDeleteByGroupId(){
        //given
        Long chatId = 23456L;
        Integer groupId = 123;
        Update update = prepareUpdate(chatId,String.format("%s %s",DELETE_GROUP_SUB.getCommandName(),groupId));
        GroupSub gs1 = new GroupSub();
        gs1.setId(123);
        gs1.setTitle("GS1 Title");
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(chatId);
        telegramUser.setGroupSubs(Collections.singletonList(gs1));
        ArrayList<TelegramUser> users = new ArrayList<>();
        users.add(telegramUser);
        gs1.setUsers(users);
        Mockito.when(groupSubService.findById(groupId)).thenReturn(Optional.of(gs1));
        Mockito.when(telegramUserService.findByChatId(chatId)).thenReturn(Optional.of(telegramUser));
        String expectedMessgae = "Removed a subscription to a group: GS1 Title";
        //when
        command.execute(update);
        //then
        users.remove(telegramUser);
        Mockito.verify(groupSubService).save(gs1);
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessgae);
    }

    @Test
    public void shouldDoesNotExistByGroupId(){
        //given
        Long chatId = 23456L;
        Integer groupId = 1234;
        Update update = prepareUpdate(chatId,String.format("%s %s",DELETE_GROUP_SUB.getCommandName(),groupId));
        Mockito.when(groupSubService.findById(groupId)).thenReturn(Optional.empty());
        String expectedMessage = "I didn't find such a group =/";
        //when
        command.execute(update);
        //then
        Mockito.verify(groupSubService).findById(groupId);
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }
}
