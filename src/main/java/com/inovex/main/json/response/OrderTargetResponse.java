package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderTargetResponse {

    private String empId;

    private String tagetedOrderQuantity;

    private String tagetedOrderValue;

    private String deliveredOrderQuantity;

    private String deliveredOrderValue;
}
