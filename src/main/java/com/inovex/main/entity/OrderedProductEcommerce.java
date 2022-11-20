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
public class OrderedProductEcommerce extends BaseEntity implements Serializable {
	

    private Long productId;

    private String productName;

    private Long receivedQty;

    private String productUnitPrice;

    private String totalPrice;

    private String discount;

    private String grandTotal;

}
