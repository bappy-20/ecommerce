package com.inovex.main.json.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveResponse {
    private long id;
    private String employeeId;
    private String employeeName;
    private String leaveType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date toDate;
    private String comment;
    private int dayCount;
    private String status;
    private boolean canDelete;
    private boolean canEdit;

}