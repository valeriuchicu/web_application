package com.endava.web_application.infrastructure.restcontroller.conversion;

import com.endava.web_application.domain.model.Employee;
import com.endava.web_application.infrastructure.restcontroller.dto.EmployeeDto;

public class EmployeeConversion {
    public static Employee toEmployee(EmployeeDto employeeDto){
        Employee employee = Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .department(employeeDto.getDepartment())
                .email(employeeDto.getEmail())
                .phoneNumber(employeeDto.getPhoneNumber())
                .salary(employeeDto.getSalary())
                .build();
        return employee;
    }

    public static EmployeeDto from(Employee employee){
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .department(employee.getDepartment())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .salary(employee.getSalary())
                .build();
        return employeeDto;
    }
}
