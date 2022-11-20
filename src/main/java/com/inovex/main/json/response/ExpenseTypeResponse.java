package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseTypeResponse {
    private long id;
    private String expenseType;
    //private String expenseUser;
    private boolean canDelete;
    private boolean canEdit;

}
