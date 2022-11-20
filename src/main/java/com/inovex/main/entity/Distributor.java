package com.inovex.main.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
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
@Table(name = "distributor")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class Distributor extends BaseEntity implements Serializable {

    private String distributorName;

    private String distributorAddress;

    private String nid;

    private String distributorOwner;

    private String distributorPhone;

    private String distributorType;

    private Long distributorCredit;

    private String tradeImage;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable
    private Set<User> users = new HashSet<>();

    public void setCustomUser(Set<User> aSet) {
        this.users.clear();
        if (aSet != null) {
            this.users.addAll(aSet);
        }
    }
    

}
