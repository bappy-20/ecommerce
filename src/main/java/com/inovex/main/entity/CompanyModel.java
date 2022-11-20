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
public class CompanyModel extends BaseEntity implements Serializable {

    private String companyName;

    private String companyOrigin;

    private String logo;
    /*
     * @OneToMany(targetEntity = BrandModel.class, cascade = CascadeType.DETACH,
     * fetch = FetchType.LAZY) private Set<BrandModel> brandModel = new HashSet<>();
     */

}
