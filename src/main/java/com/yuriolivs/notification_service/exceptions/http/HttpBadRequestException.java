package com.yuriolivs.notification_service.exceptions.http;

public class HttpBadRequestException extends RuntimeException {
  public HttpBadRequestException(String message) {
    super(message);
  }
}
