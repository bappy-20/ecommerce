package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomingProductResponse {
    private long id;
    
    private long productId;

    private String productName;

    private long purchasePrice;

    private long unitPrice;

    private String receivedQty;

    private String supplierName;
    
    private boolean canDelete;
    
    private boolean canEdit;
}
