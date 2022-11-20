package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetailTypeResponse {
    private long id;
    private String retailType;
    private boolean canDelete;
    private boolean canEdit;

}
