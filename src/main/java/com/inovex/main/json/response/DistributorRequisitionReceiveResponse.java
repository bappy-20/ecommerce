package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistributorRequisitionReceiveResponse {

    private Long productId;

    private String productName;

    private Long receivedQty;

    private String productUnitPrice;

    private String totalPrice;

    private String discount;

    private String grandTotal;

    private Long toReceive;

    private String stockQuantity;

    private String stockStatus;

}
