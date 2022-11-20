package com.inovex.main.json.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductWiseCampaignResponse {

    private long id;

    private long campaignId;

    private String campaignName;

    private long productId;

    private String productName;

    private String offerType;

    private String discountType;

    private String discountAmount;

    private String discountOn;

    private long requiredQuantity;

    private String freeProduct;

    private long freeItemQuantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date campaignStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date campaignEndDate;

}
