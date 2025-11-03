package com.gamelove.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TIMESTAMP_PROPERTY = "timestamp";

    @ExceptionHandler(GameLoveException.class)
    public ProblemDetail handleGameLoveException(GameLoveException e) {
        var errorResponse = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        errorResponse.setTitle("GameLoveException");
        errorResponse.setType(URI.create("https://www.gamelove.com/errors/generic-server-error"));
        errorResponse.setProperty(TIMESTAMP_PROPERTY, LocalDateTime.now());

        return errorResponse;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException e) {
        var errorResponse = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        errorResponse.setTitle("ResourceNotFoundException");
        errorResponse.setType(URI.create("https://www.gamelove.com/errors/resource-not-found"));
        errorResponse.setProperty(TIMESTAMP_PROPERTY, LocalDateTime.now());

        return errorResponse;
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ProblemDetail handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        var errorResponse = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());

        errorResponse.setTitle("ResourceAlreadyExistsException");
        errorResponse.setType(URI.create("https://www.gamelove.com/errors/resource-already-exists"));
        errorResponse.setProperty(TIMESTAMP_PROPERTY, LocalDateTime.now());
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException e) {
        var errorResponse = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed"
        );

        var errors = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        errorResponse.setProperty("errors", errors);
        errorResponse.setTitle("ValidationException");
        errorResponse.setProperty(TIMESTAMP_PROPERTY, LocalDateTime.now());

        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception e) {
        log.error("Unexpected error occurred", e);
        var errorResponse = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred"
        );
        errorResponse.setTitle("InternalServerError");
        errorResponse.setProperty(TIMESTAMP_PROPERTY, LocalDateTime.now());
        return errorResponse;
    }

}
