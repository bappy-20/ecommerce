package com.inovex.main.json.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseProductResponse {

    private long id;

    private String supplier;

    private long totalPrice;

    private long discount;

    private long grandTotal;

    private long totalRecieveprice;

    private String purchaseNumber;

    private Date purchaseDate;

    private String purchaseNote;

    private boolean canDelete;

    private boolean canEdit;
}
