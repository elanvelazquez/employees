package com.elanvelazquez.cognizant.challenge.employee.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

//This is the annotation when you put when you have Exception Handlers it is like a Controller.
@ControllerAdvice
public class GenericExceptionHandler  {

    @ExceptionHandler(value = {GenericRequestException.class})
    public ResponseEntity<Object> handleGendericException(GenericRequestException e)
    {
        GenericException exception = new GenericException(e.getMessage(),
                e.getStatus(),
                ZonedDateTime.now(ZoneId.of("Z"))
                );
        return new ResponseEntity<>(exception,e.getStatus());
    }

    //To handle exceptions on controller level.
    @ExceptionHandler(value = {RestClientException.class})
    public ResponseEntity<Object> handleGendericControllerException(HttpClientErrorException e)
    {
        GenericException exception = new GenericException(e.getMessage(),
                e.getStatusCode(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(exception,e.getStatusCode());
    }
}
