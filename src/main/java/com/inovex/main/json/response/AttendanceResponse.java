package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceResponse {
    private long id;
    private String employeeId;
    private String empName;
    private String empCategory;
    private long present;
    private long absent;
    private long latIn;
    private long earlyOut;
    private long inleave;
    private long total;

}
