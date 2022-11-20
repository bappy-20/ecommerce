package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceSummery {
    private String attendanceType;
    private String count;
    private String empId;
}
