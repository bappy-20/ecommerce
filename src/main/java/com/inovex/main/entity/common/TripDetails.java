package com.inovex.main.entity.common;

import javax.persistence.Column;

import org.hibernate.mapping.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripDetails {

    @Column(name = "rent")
    private String rent;

    @Column(name = "passanger")
    private String passanger;

    @Column(name = "check_point")
    private String checkPoint;

    @Column(name = "checker")
    private String checker;

    @Column(name = "images")
    private List images;

}
