package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetResponse {
    private long id;

    private String empId;

    private String empName;

    private long productId;

    private String productName;

    private String category;

    private String targetquantity;

    private String targetTotalValue;

    private String targetMonth;

    private long salequantity;

    private long saleTotalValue;
}
