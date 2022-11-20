package com.inovex.main.json.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashBackCampaignResponse {

    private Long id;

    private String campaignName;

    private Long campaignId;

    private String requiredInvoiceAmount;

    private String discountType;

    private String discountAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date campaignStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date campaignEndDate;
    
    private boolean canDelete;
    private boolean canEdit;

}
