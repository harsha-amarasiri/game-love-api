package com.gamelove.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GameLoveException.class)
    public ProblemDetail handleGameLoveException(GameLoveException e) {
        var errorResponse = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        errorResponse.setTitle("GameLoveException");
        errorResponse.setType(URI.create("https://www.gamelove.com/errors/generic-server-error"));
        errorResponse.setProperty("timestamp", LocalDateTime.now());

        return errorResponse;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException e) {
        var errorResponse = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        errorResponse.setTitle("ResourceNotFoundException");
        errorResponse.setType(URI.create("https://www.gamelove.com/errors/resource-not-found"));
        errorResponse.setProperty("timestamp", LocalDateTime.now());

        return errorResponse;
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ProblemDetail handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        var errorResponse = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());

        errorResponse.setTitle("ResourceAlreadyExistsException");
        errorResponse.setType(URI.create("https://www.gamelove.com/errors/resource-already-exists"));
        errorResponse.setProperty("timestamp", LocalDateTime.now());
        return errorResponse;
    }

}
