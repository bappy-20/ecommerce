package com.inovex.main.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inovex.main.entity.common.BaseEntity;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "retail_visit")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class RetailVisit extends BaseEntity implements Serializable {

    @Column(name = "retail_id")
    private long retailId;

    @Column(name = "retail_address")
    private String retailAddress;

    @Column(name = "lat")
    private String lat;

    @Column(name = "retail_long")
    private String retailLong;

    @Column(name = "sr_id")
    private String srId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "visit_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date visitDate;
}
