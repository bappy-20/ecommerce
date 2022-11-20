package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignTypeResponse {
    private long id;
    private String campaignType;
    private String note;
    private boolean canDelete;
    private boolean canEdit;

}
