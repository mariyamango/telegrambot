package com.github.mariyamango.tb.command;

import com.github.mariyamango.tb.javarushclient.JavaRushGroupClient;
import com.github.mariyamango.tb.service.GroupSubService;
import com.github.mariyamango.tb.service.SendBotMessageService;
import com.github.mariyamango.tb.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static java.util.Collections.singletonList;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {
    
    private CommandContainer commandContainer;
    
    @BeforeEach
    public void init(){
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        JavaRushGroupClient groupClient = Mockito.mock(JavaRushGroupClient.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubService.class);
        commandContainer = new CommandContainer(sendBotMessageService,
                telegramUserService,
                groupClient,
                groupSubService);
    }

    @Test
    public void shouldGetAllTheExistingCommands(){
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class,command.getClass());
                });
    }

    @Test
    public void shouldReturnUnknownCommand(){
        //given
        String unknownCommand = "/blablabla";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand);

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}
