package com.inovex.main.json.response;

import java.util.Set;

import com.inovex.main.entity.User;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AreaResponse {
    private long id;
    private String areaName;
    private String regionName;
    private String territoryName;
    private String marketName;
    private String address;
    private boolean canDelete;
    private boolean canEdit;
    private Set<User> users;

}
