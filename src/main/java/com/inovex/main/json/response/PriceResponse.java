package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceResponse {

    private long id;
    private long productId;

    private String productName;

    private long productPrice;

    private long dealerPrice;

    private long retailPrice;

    private long mrp;
    private boolean canDelete;
    private boolean canEdit;
}
