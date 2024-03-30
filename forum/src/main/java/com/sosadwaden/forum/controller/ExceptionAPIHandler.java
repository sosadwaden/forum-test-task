package com.sosadwaden.forum.controller;

import com.sosadwaden.forum.api.response.ErrorResponse;
import com.sosadwaden.forum.exception.MessageNotFoundException;
import com.sosadwaden.forum.exception.NoUserPermissionsException;
import com.sosadwaden.forum.exception.TopicNotFoundException;
import com.sosadwaden.forum.exception.UserNotAuthenticatedException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
public class ExceptionAPIHandler {

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTopicNotFoundException(TopicNotFoundException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotFoundException(MessageNotFoundException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Stream<String> fieldErrors = exception.getFieldErrors().stream()
                .map(e -> String.format("%s: %s", e.getField(), e.getDefaultMessage()));

        Stream<String> globalErrors = exception.getGlobalErrors().stream()
                .map(e -> String.format("%s: %s", e.getObjectName(), e.getDefaultMessage()));

        String error = Stream.concat(fieldErrors, globalErrors)
                .collect(Collectors.joining(", "));

        ErrorResponse response = ErrorResponse.builder()
                .message(error)
                .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<ErrorResponse> handleUserNotAuthenticatedException(UserNotAuthenticatedException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoUserPermissionsException.class)
    public ResponseEntity<ErrorResponse> handleNoUserPermissionsException(NoUserPermissionsException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.FORBIDDEN.value())
                .build();

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

}
