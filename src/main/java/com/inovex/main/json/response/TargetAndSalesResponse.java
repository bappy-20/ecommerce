package com.inovex.main.json.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetAndSalesResponse {
    private List<TargetResponse> target;
    private List<TargetResponse> sales;
}
