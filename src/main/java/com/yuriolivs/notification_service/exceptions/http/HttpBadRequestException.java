package com.yuriolivs.notification_service.exceptions.http;

import org.springframework.http.HttpStatus;

public class HttpBadRequestException extends HttpException {

    public HttpBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}

