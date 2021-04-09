package com.example.payroll;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice

public class FieldNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(FieldNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String FieldNotFoundHandler(FieldNotFoundException fex){
        return fex.getMessage();
    }
}
