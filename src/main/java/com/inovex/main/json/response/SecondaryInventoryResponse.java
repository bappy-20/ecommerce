package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecondaryInventoryResponse {
	private long id;
	
	private String ProductName;
	
    private Long productId;

    private Long startingInventory;

    private Long receivedInventory;

    private Long shippedInventory;

    private Long onHand;

    private Long minimumQty;

    private Long distributorId;

    private Long safetyStock;	
    
    private String distributorName;

}
