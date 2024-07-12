package com.ltfullstack.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private String code;
    private String message;
    private HttpStatus status;
}
