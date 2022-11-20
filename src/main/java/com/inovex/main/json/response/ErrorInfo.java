package com.inovex.main.json.response;

import org.springframework.http.HttpStatus;

/**
 * Error Message for Rest Api
 *
 * @author Faruk Hossain
 * @version 1.0.0
 *
 */
public class ErrorInfo {
    public final String status;
    public final String message;

    public ErrorInfo(HttpStatus httpStatus, Exception exception) {
        this.status = httpStatus.name();
        this.message = exception.getMessage();
    }

    public ErrorInfo(HttpStatus httpStatus, String message) {
        this.status = httpStatus.name();
        this.message = message;
    }
}
