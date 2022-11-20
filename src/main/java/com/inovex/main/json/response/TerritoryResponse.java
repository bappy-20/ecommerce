package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerritoryResponse {
	private long id;
    private boolean canDelete;
    private boolean canEdit;

}
