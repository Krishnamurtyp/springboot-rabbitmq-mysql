package com.example.demo.controller;

import com.example.demo.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ExceptionAdviceController {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ExceptionResponseDto> handleAllException(Exception exception) {
        if (exception instanceof MethodArgumentNotValidException)
            return handleMethodArgumentsNotValidException((MethodArgumentNotValidException) exception);
        return handleUnusualException(exception);
    }

    private ResponseEntity<ExceptionResponseDto> handleUnusualException(Exception exception) {
        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto();
        String message = Objects.requireNonNull(exception.getMessage());
        exceptionResponse.setReason(message);
        exceptionResponse.setCode(500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    private ResponseEntity<ExceptionResponseDto> handleMethodArgumentsNotValidException(MethodArgumentNotValidException exception) {
        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto();
        String message = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage();
        exceptionResponse.setReason(message);
        exceptionResponse.setCode(400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}