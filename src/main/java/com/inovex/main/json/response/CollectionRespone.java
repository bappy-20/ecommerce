package com.inovex.main.json.response;

import java.util.Date;
import java.util.List;

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
public class CollectionRespone {

    private String MarketId;

    private String retailId;

    private String MarketName;

    private String retailName;

    private Date oerderDate;

    List<NonPainResponse> orders;
}
