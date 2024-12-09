package com.keeghan.eShop.exception;

import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    // Handle generic API request exceptions
    @ExceptionHandler(value = {GenericRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(GenericRequestException ex) {
        ApiException apiException = new ApiException(
                ex.getMessage(),
                ex.getHttpStatus(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, ex.getHttpStatus());
    }

    // Handle validation errors
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiException apiException = new ApiException(
                "Validation Failed",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")),
                errors
        );

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    // Handle resource not found exceptions
    @ExceptionHandler(value = {InvalidConfigurationPropertyValueException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(InvalidConfigurationPropertyValueException ex) {
        ApiException apiException = new ApiException(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    // Handle database constraint violations
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ApiException apiException = new ApiException(
                "Data integrity violation: " + ex.getMostSpecificCause().getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

    // Handle authentication exceptions
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        ApiException apiException = new ApiException(
                "Access denied: " + ex.getMessage(),
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);
    }

    // Catch-all for any other unexpected exceptions
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        ApiException apiException = new ApiException(
                "An unexpected error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
