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
public class ProductMapping extends BaseEntity implements Serializable {

    private Long companyId;

    private String barCode;

    private Long productId;

    private String productName;

    private String sku;

    private Long productCategory;

    private Long productSubCategory;

    private String mesuringType;

    private String mesuringQuantity;

    private Long startingStock;

    private Long safetyStock;

    private Long supplierId;

    private Long brandId;

}
