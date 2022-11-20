package com.inovex.main.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.inovex.main.entity.common.BaseEntity;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "areas")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AreaModel extends BaseEntity implements Serializable {
	
	@Column(name = "area_name", unique = true)
    private String areaName;

    @OneToMany(targetEntity = TerritoryModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<TerritoryModel> territories = new HashSet<>();

    private long regionId1;

    @ManyToMany(mappedBy = "areas")
    private Set<User> users;

    public void setCustomUser(Set<User> aSet) {
        this.users.clear();
        if (aSet != null) {
            this.users.addAll(aSet);
        }
    }

}
