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
@Table(name = "product_wise_campaign")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class ProductWiseCampaign extends BaseEntity implements Serializable {

    private long campaignId;

    private long productId;

    private String offerType;

    private String discountType;

    private String discountAmount;

    private String discountOn;

    private long requiredQuantity;

    private long freeProductId;

    private long freeItemQuantity;

    private String quantity;

    private String freeItem;
}
