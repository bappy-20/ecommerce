package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
	
	private long id;
	
    private long productId;

    private String productName;

    private String sku;

    private String productLabel;

    private long productCategory;

    private String productCategoryName;

    private String shortDiscription;

    private String mesuringType;

    private String mesuringQuantity;

    private long productPrice;

    private long dealerPrice;

    private long productMrpPrice;

    private long rpPrice;

    private long startingStock;

    private long safetyStock;

    private String discountType;

    private String availableDiscount;

    private String discount;

    private String availableOffer;

    private String offer;

    private String productImage;

    private long onHand;

    private String supplier;

    private String brandName;
    
    private boolean canDelete;
    private boolean canEdit;

}
