package com.inovex.main.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

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
public class ProcessedOrderHistory extends BaseEntity implements Serializable {

    private long total;

    private long discount;

    private long grandTotal;

    @OneToMany(targetEntity = DeliveryDetails.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DeliveryDetails> deliveryDetails = new HashSet<>();

    public void setDeliveryDetails(Set<DeliveryDetails> aSet) {
        this.deliveryDetails.clear();
        if (aSet != null) {
            this.deliveryDetails.addAll(aSet);
        }
    }

}
