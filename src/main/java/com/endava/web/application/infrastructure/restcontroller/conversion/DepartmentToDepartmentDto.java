package com.endava.web.application.infrastructure.restcontroller.conversion;

import com.endava.web.application.domain.model.Department;
import com.endava.web.application.infrastructure.restcontroller.dto.DepartmentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DepartmentToDepartmentDto implements Converter<Department, DepartmentDto> {

    @Override
    public DepartmentDto convert(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        departmentDto.setLocation(department.getLocation());
        return departmentDto;
    }
}
