package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponse {
    private long id;
    private long companyId;

    private String brandName;

    private String brandOrigin;

    private String logo;
    private boolean canDelete;
    private boolean canEdit;

}
