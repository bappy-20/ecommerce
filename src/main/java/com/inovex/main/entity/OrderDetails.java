package com.inovex.main.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import com.inovex.main.entity.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
public class OrderDetails extends BaseEntity implements Serializable {

    private long productId;

    private String productName;

    private long totalPrice;

    private long productQuantity;
}
