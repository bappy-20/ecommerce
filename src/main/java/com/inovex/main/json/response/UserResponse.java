package com.inovex.main.json.response;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private long id;
    private String fullName;

    private String nickName;

    private String username;

    private String email;

    private String userType;

    private String mobile;

    private String profileImage;

    private String presentAddress;

    private String permanentAddress;

    private int age;

    private String marritalStatus;

    private String nidNumber;

    private String nidCopy;

    private String bloodGroup;
    private String driverLiecense;

    private String driverLiecenseCopy;
    private boolean canEdit;
    private boolean canDelete;
    
    private ArrayList<String> markets;
    private ArrayList<String> territories;
    private ArrayList<String> areas;
    private ArrayList<String> regions;
    
    private String territoryName;
    private String areaName;
    private String regionName;
    
    private String distributorName;


}
