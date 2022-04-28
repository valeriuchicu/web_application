package com.endava.web.application.domain.exception.exceptions;

public class DepartmentAlreadyExistsException extends RuntimeException{
    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }
}
