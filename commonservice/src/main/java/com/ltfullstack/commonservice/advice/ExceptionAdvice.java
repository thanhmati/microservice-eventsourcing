package com.ltfullstack.commonservice.advice;
import com.ltfullstack.commonservice.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(Exception ex){
        log.error("Unknown internal server error: "+ex.getMessage());
        log.error("Exception class: "+ex.getClass());
        log.error("Exception cause: "+ex.getCause());
        return new ResponseEntity<>(new ErrorMessage("9999", "Unknown internal server error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
