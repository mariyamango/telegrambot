package com.github.mariyamango.tb.javarushclient;

import com.github.mariyamango.tb.javarushclient.dto.GroupsCountRequestArgs;
import com.github.mariyamango.tb.javarushclient.dto.GroupDiscussionInfo;
import com.github.mariyamango.tb.javarushclient.dto.GroupInfo;
import com.github.mariyamango.tb.javarushclient.dto.GroupRequestArgs;

import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of the {@link JavaRushGroupClient} interface.
 */

@Component
public class JavaRushGroupClientImpl implements JavaRushGroupClient{

    private final String javarushApiGroupPath;
    private final String getJavarushApiPostPath;

    public JavaRushGroupClientImpl(@Value("${javarush.api.path}") String javarushApi) {
        this.javarushApiGroupPath = javarushApi + "/groups";
        this.getJavarushApiPostPath = javarushApi + "/posts";
    }


    @Override
    public List<GroupInfo> getGroupList(GroupRequestArgs requestArgs) {
        return Unirest.get(javarushApiGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupInfo>>() {
                })
                .getBody();
    }

    @Override
    public List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs requestArgs) {
        return Unirest.get(javarushApiGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupDiscussionInfo>>() {
                })
                .getBody();
    }

    @Override
    public Integer getGroupCount(GroupsCountRequestArgs countRequestArgs) {
        return Integer.valueOf(
                Unirest.get(String.format("%s/count", javarushApiGroupPath))
                        .queryString(countRequestArgs.populateQueries())
                        .asString()
                        .getBody()
        );
    }

    @Override
    public GroupDiscussionInfo getGroupById(Integer id) {
        return Unirest.get(String.format("%s/group%s", javarushApiGroupPath, id.toString()))
                .asObject(GroupDiscussionInfo.class)
                .getBody();
    }

    @Override
    public Integer findLastPostId(Integer groupSubId) {
        return 0;
    }
}
