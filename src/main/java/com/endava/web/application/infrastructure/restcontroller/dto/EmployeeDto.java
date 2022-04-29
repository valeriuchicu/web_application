package com.endava.web.application.infrastructure.restcontroller.dto;

import com.endava.web.application.domain.model.Department;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Department department;
    private String email;
    private String phoneNumber;
    private Double salary;
}
