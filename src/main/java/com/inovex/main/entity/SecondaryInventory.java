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
public class SecondaryInventory extends BaseEntity implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "starting_inventory")
    private Long startingInventory;

    @Column(name = "received_inventory")
    private Long receivedInventory;

    @Column(name = "shipped_inventory")
    private Long shippedInventory;

    @Column(name = "on_hand")
    private Long onHand;

    @Column(name = "minimum_qty")
    private Long minimumQty;

    @Column(name = "distributor_id")
    private Long distributorId;

    @Column(name = "safety_stock")
    private Long safetyStock;
}