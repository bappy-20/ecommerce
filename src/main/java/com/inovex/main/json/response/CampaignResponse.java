package com.inovex.main.json.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignResponse {
    private long id;
    private String campaignName;

    private String campaignType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expiredDate;

    private String status;
    private boolean canDelete;
    private boolean canEdit;

}
