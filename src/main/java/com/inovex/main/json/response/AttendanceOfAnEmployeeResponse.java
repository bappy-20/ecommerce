package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceOfAnEmployeeResponse {

    private long id;
    private String EmployeeId;
    private String EmployeeName;
    private String EmployeeType;
    private String Date;
    private String inTime;
    private String addressIn;
    private String outTime;
    private String addressOut;
    private String status;

}
