package com.ltfullstack.commonservice.advice;

import com.ltfullstack.commonservice.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName,message);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage("9999", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
