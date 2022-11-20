package com.inovex.main.entity;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDashboardReponse {
    private long numberOfOrder;
    private long grandTotal;
}
