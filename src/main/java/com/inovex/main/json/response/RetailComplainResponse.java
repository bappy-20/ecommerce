package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetailComplainResponse {
    private long id;

    private String title;

    private long retailId;

    private String note;

    private String employeeId;
    private boolean canDelete;
    private boolean canEdit;

}
