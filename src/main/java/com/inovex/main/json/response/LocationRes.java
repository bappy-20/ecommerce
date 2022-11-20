package com.inovex.main.json.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRes {
    private String lat;
    private String lng;
    private String empName;
    private Date date;
    private String address;

}
