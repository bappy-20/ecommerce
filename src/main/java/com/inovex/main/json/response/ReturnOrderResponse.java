package com.inovex.main.json.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnOrderResponse {

    private long id;

    private String retailName;

    private long totalPrice;

    private String orderNumber;

    private Date returnDate;

    private String returnNote;
    
    private boolean canDelete;
    
    private boolean canEdit;
}
