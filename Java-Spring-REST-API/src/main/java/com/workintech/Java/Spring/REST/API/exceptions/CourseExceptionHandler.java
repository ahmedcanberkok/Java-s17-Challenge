package com.workintech.Java.Spring.REST.API.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CourseExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CourseErrorResponse> handleException (CourseException exception) {
        CourseErrorResponse response = new CourseErrorResponse(exception.getStatus().value(),
                exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response,exception.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<CourseErrorResponse> handleException (Exception exception) {
        CourseErrorResponse response = new CourseErrorResponse(HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }


}
