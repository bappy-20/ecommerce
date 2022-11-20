package com.inovex.main.json.response;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductPriceCalResponse {

    private long id;
    private long statusCode;
    private long totalPrice;
    private long discounted;
    private long stockQuantity;
    private String message;
}
