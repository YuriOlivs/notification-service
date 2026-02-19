package com.yuriolivs.notification_service.exceptions.http;

import org.springframework.http.HttpStatus;

public class HttpForbiddenException extends HttpException {

    public HttpForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}

