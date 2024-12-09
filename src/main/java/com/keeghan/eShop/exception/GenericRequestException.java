package com.keeghan.eShop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericRequestException extends RuntimeException {
    private final HttpStatus httpStatus;

    public GenericRequestException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public GenericRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public GenericRequestException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

}
