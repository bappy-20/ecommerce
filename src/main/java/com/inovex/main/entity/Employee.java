package com.inovex.main.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inovex.main.entity.common.BaseEntity;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class Employee extends BaseEntity implements Serializable {

    @Column(name = "employee_id", unique = true)
    private String employeeId;

    @Column(name = "emp_name")
    private String empName;

    @Column(name = "password")
    private String password;

    @Column(name = "emp_address")
    private String empAddress;

    @Column(name = "emp_phone")
    private String empPhone;

    @Column(name = "emp_category")
    private long empCategory;

    @Column(name = "reporting_id")
    private String reportingId;

    private String empImage;

    private String empNid;

    private String emergencyContact;

    private String fatherName;

    private String motherName;

    private String activeStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String mobileBankInfo;

    private String generalBankInfo;

    @OneToMany(targetEntity = Market.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Market> markets = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    private Set<TerritoryModel> territories;

}
