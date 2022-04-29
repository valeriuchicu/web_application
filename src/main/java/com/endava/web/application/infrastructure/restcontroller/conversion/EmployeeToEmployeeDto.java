package com.endava.web.application.infrastructure.restcontroller.conversion;

import com.endava.web.application.domain.model.Employee;
import com.endava.web.application.infrastructure.restcontroller.dto.EmployeeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToEmployeeDto implements Converter<Employee, EmployeeDto> {
    @Override
    public EmployeeDto convert(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .department(employee.getDepartment())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .salary(employee.getSalary())
                .build();
    }
}
