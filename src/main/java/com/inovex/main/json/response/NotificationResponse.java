package com.inovex.main.json.response;

import javax.persistence.Lob;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {
    private long id;
    private String imei;

    private String notificationType;

    private String title;

    @Lob
    private String message;

    private String fileName;

    private String employeeId;
    private boolean canDelete;
    private boolean canEdit;

}
