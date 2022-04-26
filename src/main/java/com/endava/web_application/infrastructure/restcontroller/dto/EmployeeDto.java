package com.endava.web_application.infrastructure.restcontroller.dto;

import com.endava.web_application.domain.model.Department;
import lombok.*;
import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {

    private Integer id;

    @NotBlank(message = "The first name cannot be empty")
    @Size(min = 2, message = "The first name should contain at least two symbols")
    private String firstName;

    @NotBlank(message = "The last name cannot be empty")
    @Size(min = 2, message = "The last name should contain at least two symbols")
    private String lastName;

    private Department department;

    @NotBlank(message = "The email cannot be empty")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "The phone number cannot be empty")
    @Column(unique = true)
    @Pattern(regexp = "\\d{3}.\\d{3}.\\d{4}", message = "Please use pattern XXX-XXX-XXXX")
    private String phoneNumber;

    @DecimalMin(value = "1.00",message = "Salary must be >= 1.0")
    private Double salary;
}

