package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInventoryResponse {

    private long productId;

    private String productName;

    private String sku;

    private String productLabel;

    private String productCategoryName;

    private String mesuringType;

    private long productPrice;

    private long productMrpPrice;

    private long startingStock;

    private long safetyStock;

    private String productImage;

    private String supplier;

    private String brandName;

    private long totalRecieve;

    private long totalShipped;

    private long onHand;

    private long totalSaleAmount;

}
