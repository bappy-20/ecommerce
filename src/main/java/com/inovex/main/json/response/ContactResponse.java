package com.inovex.main.json.response;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {

    private String name;

    private String address;

    private String phone;

    private String type;

}
