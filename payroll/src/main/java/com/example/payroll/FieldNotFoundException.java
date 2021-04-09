package com.example.payroll;

public class FieldNotFoundException extends RuntimeException{
    public FieldNotFoundException(String fieldName){
        super("Not matching data field found: " + fieldName);
    }
}
