package com.yuriolivs.notification_service.exceptions.http;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    public HttpException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
