package com.inovex.main.json.response;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RetailResponse {
    private long id;
    private String retailName;

    private String retailType;

    private String retailAddress;

    private String retailOwner;

    private String retailPhone;

    private String retailLatLong;

    private String nationalId;

    private String marketName;

    private String submittedBy;

    private String approvedBy;

    private String status;

    private String storeImage1;
    
    private boolean canDelete;
    
    private boolean canEdit;

}
