package com.inovex.main.entity;

import java.io.Serializable;

import javax.persistence.Column;
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
public class ReturnProduct extends BaseEntity implements Serializable {

    @Column(name = "product_id1")
    private String productId1;

    private String productName;

    private String productIdUnitprice;

    @Column(name = "purchase_price")
    private String purchasePrice;

    @Column(name = "received_qty")
    private String receivedQty;

    @Column(name = "supplier_id")
    private String supplierId;

    private String returnQuantity;

    private String deductionAmount;

    private String total;
}
