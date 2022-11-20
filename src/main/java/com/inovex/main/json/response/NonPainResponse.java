package com.inovex.main.json.response;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NonPainResponse {

    private String orderId;

    private long total;

    private long discount;

    private long grandTotal;

    private String oerderDate;

    private long due;

}
