package com.endava.web_application.infrastructure.restcontroller.conversion;

import com.endava.web_application.domain.model.Department;
import com.endava.web_application.infrastructure.restcontroller.dto.DepartmentDto;

public class DepartmentConversion {
    public static DepartmentDto from(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        departmentDto.setLocation(department.getLocation());
        return departmentDto;
    }

    public static Department toDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setId(departmentDto.getId());
        department.setName(departmentDto.getName());
        department.setLocation(departmentDto.getLocation());
        return department;
    }
}
