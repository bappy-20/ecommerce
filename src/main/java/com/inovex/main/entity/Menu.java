package com.inovex.main.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.inovex.main.entity.common.BaseEntity;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class Menu extends BaseEntity implements Serializable {

    @Column(name = "role_name", unique = true)
    private String roleName;

    @OneToMany(targetEntity = SubMenu.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<SubMenu> subMenu = new HashSet<>();

    public void setChildren(Set<SubMenu> aSet) {
        // this.sonEntities = aSet; //This will override the set that Hibernate is
        // tracking.
        this.subMenu.clear();
        if (aSet != null) {
            this.subMenu.addAll(aSet);
        }
    }

}
