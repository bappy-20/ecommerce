package com.inovex.main.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "campaign")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "builder", toBuilder = true)
@AllArgsConstructor
public class Campaign extends BaseEntity implements Serializable {

    @Column(name = "campaign_name")
    private String campaignName;

    @Column(name = "campaign_type")
    private String campaignType;

    @Column(name = "start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startTime;

    @Column(name = "expired_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expiredDate;

    @Column(name = "status")
    private Long status;
}
