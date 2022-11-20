package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {
    private long id;
    private String name;

    private String description;
    private boolean canEdit;
    private boolean canDelete;

}
