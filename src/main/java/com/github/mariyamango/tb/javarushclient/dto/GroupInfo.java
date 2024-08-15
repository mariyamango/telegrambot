package com.github.mariyamango.tb.javarushclient.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Group Info DTO class.
 */

@Data
@ToString
public class GroupInfo {
    private Integer id;
    private String avatarUrl;
    private String createTime;
    private String description;
    private String key;
    private GroupLanguage language;
    private Integer levelToEditor;
    private MeGroupInfo meGroupInfo;
    private String pictureUrl;
    private String title;
    private String type;
    private Integer usersCount;
    private GroupVisibilityStatus visibilityStatus;
}