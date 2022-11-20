package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistributorResponse {
    private long id;
    private String distributorName;

    private String distributorAddress;

    private String nid;

    private String distributorOwner;

    private String distributorPhone;

    private String distributorType;

    private Long distributorCredit;

    private String tradeImage;
    private boolean canDelete;
    private boolean canEdit;

}
