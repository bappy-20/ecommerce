package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayModelResponse {
    private long id;
    private String empId;

    private String dayName;

    private long routeId;
    private boolean canDelete;
    private boolean canEdit;

}
