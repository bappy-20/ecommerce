package com.inovex.main.json.response;

import com.inovex.main.entity.RouteModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteResponse {

    private String empId;
    private String dayName;
    private RouteModel route;

    private String routeName;

    private String regionName;

    private String areaName;

    private String territoryName;

    private String marketName;
    private long id;
    private boolean canDelete;
    private boolean canEdit;

}
