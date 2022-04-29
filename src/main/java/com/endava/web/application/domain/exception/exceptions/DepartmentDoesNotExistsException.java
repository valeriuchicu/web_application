package com.endava.web.application.domain.exception.exceptions;

public class DepartmentDoesNotExistsException extends RuntimeException{
    public DepartmentDoesNotExistsException(String message) {
        super(message);
    }
}
