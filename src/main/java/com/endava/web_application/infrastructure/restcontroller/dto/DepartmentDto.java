package com.endava.web_application.infrastructure.restcontroller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentDto {

    private Integer id;

    @NotBlank(message = "The department name cannot be empty")
    private String name;

    @NotBlank(message = "The name of location cannot be empty")
    private String location;
}
