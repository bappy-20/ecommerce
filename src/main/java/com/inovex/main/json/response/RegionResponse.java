package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionResponse {
    private long id;
    private String regionName1;
    private boolean canDelete;
    private boolean canEdit;

}
