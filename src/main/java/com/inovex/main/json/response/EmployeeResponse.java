package com.inovex.main.json.response;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {

    private long id;

    private long orgId;

    private String employeeId;

    private String empName;
    private String password;

    private String empAddress;

    private String empPhone;

    private String empCategory;

    private String reportingId;

    private String reportingName;

    private long distributorId;

    private String distributorName;

    private String distributorAddress;

    private String distributorMobile;

    private String reportingMobile;

    private String territoryName;

    private String areaName;

    private String regionName;
    private String empImage;
    private String activeStatus;

    private boolean canEdit;

    private boolean canDelete;

    private Set<MarketResponse> markets;

}
