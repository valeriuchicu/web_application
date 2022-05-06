package com.endava.web.application.infrastructure.restcontroller.dto;

import com.endava.web.application.domain.model.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Department department;
    private String email;
    private String phoneNumber;
    private Double salary;
}
