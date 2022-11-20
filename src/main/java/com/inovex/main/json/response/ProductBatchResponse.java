package com.inovex.main.json.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBatchResponse {

    private long id;

    private long productId;

    private String productName;

    private Date expireDate;

    private Date manufatureDate;

    private String batchNumber;

    private boolean canDelete;

    private boolean canEdit;
}
