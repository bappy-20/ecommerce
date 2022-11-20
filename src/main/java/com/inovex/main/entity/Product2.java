package com.inovex.main.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.inovex.main.entity.common.BaseEntity;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class Product2 extends BaseEntity implements Serializable {

    @Column(name = "product_name")
    private String productName;

    @Column(name = "sku")
    private String sku;

    @Column(name = "product_label")
    private String productLabel;

    @Column(name = "product_category")
    private long productCategory;

    @Column(name = "short_description")
    private String shortDiscription;

    @Column(name = "mesuring_type")
    private String mesuringType;

    @Column(name = "product_price")
    private long productPrice;

    @Column(name = "product_mrp_price")
    private long productMrpPrice;

    @Column(name = "starting_stock")
    private long startingStock;

    @Column(name = "safety_stock")
    private long safetyStock;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "available_discount")
    private String availableDiscount;

    @Column(name = "discount")
    private boolean discount;

    @Column(name = "available_offer")
    private String availableOffer;

    @Column(name = "offer")
    private boolean offer;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "supplier_id")
    private long supplierId;

    private long brandId;

}
