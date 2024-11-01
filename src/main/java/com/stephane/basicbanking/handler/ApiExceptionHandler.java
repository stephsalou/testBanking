package com.stephane.basicbanking.handler;


import com.stephane.basicbanking.customException.NullArgumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({NullArgumentException.class})
    public ResponseEntity<Object> handleNullArgumentException(NullArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
