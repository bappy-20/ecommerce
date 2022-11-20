package com.inovex.main.json.response;

import java.util.Set;

import com.inovex.main.entity.Retail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketResponse {

    private String marketName;

    private String address;

    private Set<Retail> retails;
    private long id;
    private boolean canDelete;
    private boolean canEdit;

}
