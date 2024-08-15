package com.github.mariyamango.tb.javarushclient;

import com.github.mariyamango.tb.javarushclient.dto.GroupsCountRequestArgs;
import com.github.mariyamango.tb.javarushclient.dto.GroupDiscussionInfo;
import com.github.mariyamango.tb.javarushclient.dto.GroupInfo;
import com.github.mariyamango.tb.javarushclient.dto.GroupRequestArgs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.mariyamango.tb.javarushclient.dto.GroupInfoType.TECH;

@DisplayName("Integration-level testing for JavaRushGroupClientImplTest")
public class JavaRushGroupClientTest {
    public static final String JAVARUSH_API_PATH = "https://javarush.ru/api/1.0/rest";

    private final JavaRushGroupClient groupClient = new JavaRushGroupClientImpl(JAVARUSH_API_PATH);

    @Test
    public void shouldProperlyGetGroupsWithEmptyArgs(){
        //given
        GroupRequestArgs args = GroupRequestArgs.builder().build();
        //when
        List<GroupInfo> groupList = groupClient.getGroupList(args);
        //then
        Assertions.assertNotNull(groupList);
        Assertions.assertFalse(groupList.isEmpty());
    }

    @Test
    public void shouldProperlyGetWithOffSetAndLimit(){
        //given
        GroupRequestArgs args = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();
        //when
        List<GroupInfo> groupList = groupClient.getGroupList(args);
        //then
        Assertions.assertNotNull(groupList);
        Assertions.assertEquals(3,groupList.size());
    }

    @Test
    public void shouldProperlyGetGroupsDiscWithEmptyArgs(){
        //given
        GroupRequestArgs args = GroupRequestArgs.builder().build();
        //when
        List<GroupInfo> groupList = groupClient.getGroupList(args);
        //then
        Assertions.assertNotNull(groupList);
        Assertions.assertFalse(groupList.isEmpty());
    }

    @Test
    public void shouldProperlyGetGroupDiscWithOffSetAndLimit(){
        //given
        GroupRequestArgs args = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();
        //when
        List<GroupInfo> groupList = groupClient.getGroupList(args);
        //then
        Assertions.assertNotNull(groupList);
        Assertions.assertEquals(3,groupList.size());
    }

    @Test
    public void shouldProperlyGetGroupCount(){
        //given
        GroupsCountRequestArgs args = GroupsCountRequestArgs.builder().build();
        //when
        Integer groupCount = groupClient.getGroupCount(args);
        //then
        Assertions.assertEquals(30,groupCount);
    }

    @Test
    public void shouldProperlyGetGroupTECHCount(){
        //given
        GroupsCountRequestArgs args = GroupsCountRequestArgs.builder()
                .type(TECH)
                .build();
        //when
        Integer groupCount = groupClient.getGroupCount(args);
        //then
        Assertions.assertEquals(7,groupCount);
    }

    @Test
    public void shouldProperlyGetGroupById(){
        //given
        Integer androidGroupId = 7;
        //when
        GroupDiscussionInfo groupById = groupClient.getGroupById(androidGroupId);
        //then
        Assertions.assertNotNull(groupById);
        Assertions.assertEquals(7,groupById.getId());
        Assertions.assertEquals(TECH,groupById.getType());
        Assertions.assertEquals("android",groupById.getKey());
    }
}
