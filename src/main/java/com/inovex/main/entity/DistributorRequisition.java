package com.inovex.main.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

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
public class DistributorRequisition extends BaseEntity implements Serializable {

    private Long dealerId;

    private String requisitionNumber;

    private String status;

    private Long productWiseOfferId;

    private String cashBackId;

    private String totalPrice;

    private String netDiscount;

    private String grandTotal;

    private String totalPriceOfSupplyChain;

    private String netDiscountOfSupplyChain;

    private String grandTotalOfSupplyChain;

    private String totalPriceOfAccounce;

    private String netDiscountOfAccounce;

    private String grandTotalOfAccounce;

    private String totalPriceOfOperation;

    private String netDiscountOfOperation;

    private String grandTotalOfOperation;

    private String totalPriceOfDelivery;

    private String netDiscountOfDelivery;

    private String grandTotalOfDelivery;

    private String deliveryStatus;

    @OneToMany(targetEntity = DistributorRequisitionProduct.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DistributorRequisitionProduct> distributorRequiredProduct = new HashSet<>();

    @OneToMany(targetEntity = DistributorRequisitionReceive.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DistributorRequisitionReceive> distributorRequiredReceive = new HashSet<>();

    @OneToMany(targetEntity = DistributorRequisitionFreeProduct.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DistributorRequisitionFreeProduct> freeProductlist = new HashSet<>();

    @OneToMany(targetEntity = DistributorRequisitionOperationApprove.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DistributorRequisitionOperationApprove> requisitionOperationApprove = new HashSet<>();

    @OneToMany(targetEntity = DistributorRequisitionAccounceApprove.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DistributorRequisitionAccounceApprove> requisitionAccounceApprove = new HashSet<>();

    @OneToMany(targetEntity = DistributorRequisitionSupplyChainApprove.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DistributorRequisitionSupplyChainApprove> requisitionSupplyChainApprove = new HashSet<>();

    @OneToMany(targetEntity = DistributorRequisitionDelivered.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DistributorRequisitionDelivered> requisitionDelivered = new HashSet<>();

    public void setChildren(Set<DistributorRequisitionProduct> aSet) {

        this.distributorRequiredProduct.clear();
        if (aSet != null) {
            this.distributorRequiredProduct.addAll(aSet);
        }
    }

    public void setFreeProduct(Set<DistributorRequisitionFreeProduct> aSet) {
        this.freeProductlist.clear();
        if (aSet != null) {
            this.freeProductlist.addAll(aSet);
        }
    }

    public void setOperationApprove(Set<DistributorRequisitionOperationApprove> aSet) {
        this.requisitionOperationApprove.clear();
        if (aSet != null) {
            this.requisitionOperationApprove.addAll(aSet);
        }
    }

    public void setAccounceApprove(Set<DistributorRequisitionAccounceApprove> aSet) {
        this.requisitionAccounceApprove.clear();
        if (aSet != null) {
            this.requisitionAccounceApprove.addAll(aSet);
        }
    }

    public void setSupplyChainApprove(Set<DistributorRequisitionSupplyChainApprove> aSet) {
        this.requisitionSupplyChainApprove.clear();
        if (aSet != null) {
            this.requisitionSupplyChainApprove.addAll(aSet);
        }
    }

    public void setDelivered(Set<DistributorRequisitionDelivered> aSet) {
        this.requisitionDelivered.clear();
        if (aSet != null) {
            this.requisitionDelivered.addAll(aSet);
        }
    }

    public void setProductRecieve(Set<DistributorRequisitionReceive> aSet) {
        this.distributorRequiredReceive.clear();
        if (aSet != null) {
            this.distributorRequiredReceive.addAll(aSet);
        }
    }
}
