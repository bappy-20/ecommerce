package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponse {
    private long id;
    private String companyName;

    private String companyOrigin;

    private String logo;
    private boolean canDelete;
    private boolean canEdit;
}
