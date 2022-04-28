package com.endava.web.application.infrastructure.restcontroller.conversion;

import com.endava.web.application.infrastructure.restcontroller.dto.DepartmentDto;
import com.endava.web.application.domain.model.Department;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DepartmentDtoToDepartment implements Converter<DepartmentDto, Department> {

    @Override
    public Department convert(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setId(departmentDto.getId());
        department.setName(departmentDto.getName());
        department.setLocation(departmentDto.getLocation());
        return department;
    }
}
