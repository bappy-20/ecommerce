package com.inovex.main.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistributorRequisitionResponse {

    private Long id;

    private String DistName;

    private String requisitionNumber;

    private String status;

    private String productWiseOfferId;

    private String cashBackId;

    private String totalPrice;

    private String netDiscount;

    private String grandTotal;

    private boolean canDelete;

    private boolean canEdit;

    private String deliveryStatus;

}
