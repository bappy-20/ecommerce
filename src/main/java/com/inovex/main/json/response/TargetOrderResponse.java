package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetOrderResponse {
	private long id;
	
	private String empId;
	
	private String empName;
	
	private String orderQuantity;
	
	private String orderValue;
	
	private String targetMonth;

}
