package com.inovex.main.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.inovex.main.entity.common.BaseEntity;
import com.inovex.main.file.File;

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
public class Retail extends BaseEntity implements Serializable {

    @Column(name = "retail_name")
    private String retailName;

    private long retailType;

    @Column(name = "retail_address")
    private String retailAddress;

    @Column(name = "retail_owner")
    private String retailOwner;

    @Column(name = "retail_phone")
    private String retailPhone;

    @Column(name = "retail_lat")
    private String retailLat;

    @Column(name = "retail_long")
    private String retailLong;

    @Column(name = "distributor_id")
    private long distributorId;

    private String nationalId;

    private long marketId;

    private String submittedBy;

    private long approvedBy;

    private String status;

    private String storeImage1;

    @Column(name = "approved_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    @OneToMany(targetEntity = File.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<File> storeImage = new HashSet<>();
}
