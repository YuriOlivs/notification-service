package com.yuriolivs.notification_service.exceptions.http;

import org.springframework.http.HttpStatus;

public class HttpInternalServerErrorException extends HttpException {

    public HttpInternalServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
