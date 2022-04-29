package com.endava.web.application.infrastructure.restcontroller;

import com.endava.web.application.domain.exception.exceptions.*;
import com.endava.web.application.infrastructure.restcontroller.dto.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class EmployeeGlobalExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NoSuchEmployeeException.class
            , NoSuchDepartmentException.class
            , DepartmentAlreadyExistsException.class
            , DepartmentDoesNotExistsException.class
            , DepartmentConstraintsException.class
            , EmployeeConstraintsException.class})
    public ResponseEntity<CustomErrorResponse> customHandleNotFoundID(Exception ex) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getLocalizedMessage());
        errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
