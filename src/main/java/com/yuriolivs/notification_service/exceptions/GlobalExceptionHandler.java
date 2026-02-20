package com.yuriolivs.notification_service.exceptions;

import com.yuriolivs.notification_service.exceptions.http.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorResponse> handleHttpException(
            HttpException ex,
            HttpServletRequest request
    ) {
        ErrorResponse err = new ErrorResponse(
                ex.getStatus(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(err.getStatusCode())
                .body(err);
    }
}
