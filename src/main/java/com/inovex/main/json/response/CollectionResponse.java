package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionResponse {
	 private long id;
	 private String orderId;
	 private long total;
	 private long recieveAmount;
	 private long dueAmount;
	 private String collectionDate;
	 private String totalRecivedAmount;
	 private String totalDueAmount;
	 private String totalTotalAmount;

}
