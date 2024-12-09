package com.keeghan.eShop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

@Getter
public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
    private final Map<String, String> validationErrors;

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this(message, httpStatus, timestamp, null);
    }

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp, Map<String, String> validationErrors) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.validationErrors = validationErrors;
    }
}
