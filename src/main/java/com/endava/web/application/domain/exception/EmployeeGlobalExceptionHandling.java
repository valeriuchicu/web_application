package com.endava.web.application.domain.exception;

import com.endava.web.application.domain.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class EmployeeGlobalExceptionHandling extends ResponseEntityExceptionHandler {
    CustomErrorResponse errors = new CustomErrorResponse();

    @ExceptionHandler({NoSuchEmployeeException.class
            , NoSuchDepartmentException.class
            , DepartmentAlreadyExistsException.class
            , DepartmentDoesNotExistsException.class
            , DepartmentConstraintsException.class
            , EmployeeConstraintsException.class})
    public ResponseEntity<CustomErrorResponse> customHandleNotFoundID(Exception ex, WebRequest request) {
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
