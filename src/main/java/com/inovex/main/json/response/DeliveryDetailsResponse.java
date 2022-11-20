package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDetailsResponse {
    private long productId;

    private String productName;

    private long totalPrice;

    private long productQuantity;

    private String discountType;

    private long discount;

    private long discountedPrice;
}
