package com.inovex.main.entity;

import javax.persistence.Column;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder(builderClassName = "Builder", toBuilder = true)
public class Password {

    @Column(name = "password")
    private String password;

    @Column(name = "corfirm_password")
    private String corfirmPassword;

}
