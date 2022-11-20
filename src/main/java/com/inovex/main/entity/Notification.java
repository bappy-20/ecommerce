package com.inovex.main.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Lob;

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
public class Notification extends BaseEntity implements Serializable {

    private String imei;

    private String notificationType;

    private String title;

    @Lob
    private String message;

    private String fileName;

    private String employeeId;

}
