package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierResponse {
    private long id;
    private String name;

    private String address;

    private String phone;

    private String note;
    private boolean canDelete;
    private boolean canEdit;

}
