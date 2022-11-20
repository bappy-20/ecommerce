package com.inovex.main.json.response;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseResponse {

    private long id;

    private String expenseType;

    private String amount;

    private String note;

    private String expenseBy;

    private String approvedBy;

    private String status;

    private String approvedAmount;

    private String attachment;
   
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expenseDate;
    
    private boolean canDelete;
    private boolean canEdit;

}
