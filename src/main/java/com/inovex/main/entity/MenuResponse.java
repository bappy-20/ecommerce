package com.inovex.main.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuResponse {
    private long id;
    private String roleName;
    private String description;
    private boolean canDelete;
    private boolean canEdit;

}