package com.inovex.main.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.inovex.main.entity.common.BaseEntity;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assign_routes")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class AssignRouteModel extends BaseEntity implements Serializable {

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "territory_name")
    private String territoryName;

    @Column(name = "distributor_name")
    private String distributorName;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "day_name")
    private String dayName;

    @Column(name = "route_details")
    private String routeDetails;

}
