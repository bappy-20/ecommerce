package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationResponse {

    private long id;

    private String orgName;

    private String contactName;

    private String mobile;

    private String email;

    private String address;

    private long subscriptionNumber;

    private long subscriptionPeriod;

    private String website;

    private String logo;

    private String about;

    private String transportType;

    private String ownerName;

    private boolean canDelete;

    private boolean canEdit;
}
