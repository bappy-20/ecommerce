package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMappingResponse {
    private long id;
    private Long companyId;

    private String barCode;

    private Long productId;

    private String productName;

    private String sku;

    private Long productCategory;

    private Long productSubCategory;

    private String mesuringType;

    private String mesuringQuantity;

    private Long startingStock;

    private Long safetyStock;

    private Long supplierId;

    private Long brandId;

    private boolean canDelete;
    private boolean canEdit;

}
