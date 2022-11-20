package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTypeResponse {
    private long id;
    private String userType;

    private String note;
    private boolean canEdit;
    private boolean canDelete;

}
