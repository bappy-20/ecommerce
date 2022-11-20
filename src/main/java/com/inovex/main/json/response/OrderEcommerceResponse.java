package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEcommerceResponse {

    private Long id;

    private String buyerName;

    private String orderNumber;

    private String status;

    private String totalPrice;

    private String netDiscount;

    private String grandTotal;

    private boolean canDelete;

    private boolean canEdit;

}
