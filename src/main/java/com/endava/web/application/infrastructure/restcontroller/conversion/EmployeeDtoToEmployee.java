package com.endava.web.application.infrastructure.restcontroller.conversion;

import com.endava.web.application.domain.model.Employee;
import com.endava.web.application.infrastructure.restcontroller.dto.EmployeeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDtoToEmployee implements Converter<EmployeeDto, Employee> {
    @Override
    public Employee convert(EmployeeDto employeeDto) {
        return Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .department(employeeDto.getDepartment())
                .email(employeeDto.getEmail())
                .phoneNumber(employeeDto.getPhoneNumber())
                .salary(employeeDto.getSalary())
                .build();
    }
}
