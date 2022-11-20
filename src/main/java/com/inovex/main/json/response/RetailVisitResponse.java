package com.inovex.main.json.response;

import java.util.Date;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class RetailVisitResponse {

    private long retailId;

    private String retailName;

    private String retailAddress;

    private String systemAddress;

    private String lat;

    private String retailLong;

    private String srId;

    private Date visitDate;
}
