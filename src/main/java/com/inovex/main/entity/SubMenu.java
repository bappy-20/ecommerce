package com.inovex.main.entity;

import java.io.Serializable;

import javax.persistence.Entity;

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
public class SubMenu extends BaseEntity implements Serializable {

    private String subMenuName;

    private boolean canRead;

    private boolean canEdit;

    private boolean canCreate;

    private boolean canDelete;

}
