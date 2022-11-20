package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrimaryInventoryResponse {
	private long id;
	private long productId;
	private String productName;
	private long safetyStock;
	private long onHand;
	private long startingStock;
	private long receivedInventory;
	private Long shippedInventory;

}
