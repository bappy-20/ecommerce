package com.inovex.main.entity;

import java.io.Serializable;

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
@Table(name = "incoming_product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class IncomingProduct extends BaseEntity implements Serializable {

    private long productId1;

    private long productIdUnitprice;

    private long purchasePrice;

    private String discount;

    private String discountType;

    private String grandPrice;

    private long productBatchId;

    private String receivedQty;

    private long supplierId;

}
