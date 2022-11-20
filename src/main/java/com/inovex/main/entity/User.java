package com.inovex.main.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.inovex.main.entity.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User extends BaseEntity implements Serializable {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    @Transient
    private String password;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "present_address")
    private String presentAddress;

    @Column(name = "permanent_address")
    private String permanentAddress;

    @Column(name = "age")
    private int age;

    @Column(name = "marrital_status")
    private String marritalStatus;

    @Column(name = "nid_number")
    private String nidNumber;

    @Column(name = "nid_copy")
    private String nidCopy;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "driver_liecense")
    private String driverLiecense;

    @Column(name = "driver_liecense_copy")
    private String driverLiecenseCopy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expired_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expiredDate;

    private Long dealerId;

    private Long orgId;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    private Set<AreaModel> areas;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    private Set<TerritoryModel> territories;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    private Set<RegionModel> regions;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    private Set<Market> mkts;
    
    @ManyToMany(mappedBy = "users")
    private Set<Distributor> distributors;
    
    public void setCustomDistributor(Set<Distributor> aSet) {
        this.distributors.clear();
        if (aSet != null) {
            this.distributors.addAll(aSet);
        }
    }

    public void setCustomAreaModel(Set<AreaModel> aSet) {
        this.areas.clear();
        if (aSet != null) {
            this.areas.addAll(aSet);
        }
    }

    public void setCustomTerritoryModel(Set<TerritoryModel> aSet) {
        this.territories.clear();
        if (aSet != null) {
            this.territories.addAll(aSet);
        }
    }

    public void setCustomRegionModel(Set<RegionModel> aSet) {
        this.regions.clear();
        if (aSet != null) {
            this.regions.addAll(aSet);
        }
    }

    public void setCustomMarket(Set<Market> aSet) {
        this.mkts.clear();
        if (aSet != null) {
            this.mkts.addAll(aSet);
        }
    }

}
