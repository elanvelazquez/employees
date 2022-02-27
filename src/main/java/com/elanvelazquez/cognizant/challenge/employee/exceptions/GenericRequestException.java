package com.elanvelazquez.cognizant.challenge.employee.exceptions;

import org.springframework.http.HttpStatus;

//This is like a DAO
public class GenericRequestException extends RuntimeException{
    private HttpStatus status;

    public GenericRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
