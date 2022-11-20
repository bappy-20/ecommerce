package com.inovex.main.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.inovex.main.entity.common.BaseEntity;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "routes")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class RouteModel extends BaseEntity implements Serializable {

    @Column(name = "route_name")
    private String routeName;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "territory_name")
    private String territoryName;

    @Column(name = "market_name")
    private String marketName;

    @OneToMany(targetEntity = Market.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Market> markets = new HashSet<>();

}
