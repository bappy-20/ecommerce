package com.inovex.main.json.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private long id;
    private String name;
    private boolean canEdit;
    private boolean canDelete;

}
