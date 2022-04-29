package com.endava.web.application.infrastructure.restcontroller;

import com.endava.web.application.domain.model.Department;
import com.endava.web.application.domain.service.DepartmentService;
import com.endava.web.application.infrastructure.restcontroller.dto.DepartmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DepartmentController {

    private final Converter<DepartmentDto, Department> departmentDtoToDepartmentConverter;
    private final Converter<Department, DepartmentDto> departmentToDepartmentDtoConverter;
    private final DepartmentService departmentService;

    @GetMapping("/departments")
    public List<Department> showAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/departments/{id}")
    public Department getDepartment(@PathVariable int id) {

        return departmentService.getDepartment(id);
    }

    @PostMapping("/departments")
    public ResponseEntity<DepartmentDto> addNewDepartment(@RequestBody DepartmentDto departmentDto) {
        final Department department = departmentDtoToDepartmentConverter.convert(departmentDto);
        final Department savedDepartment = departmentService.saveDepartment(department);

        return new ResponseEntity<>(departmentToDepartmentDtoConverter.convert(savedDepartment), HttpStatus.OK);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department, @PathVariable int id) {
        return new ResponseEntity<>(departmentService.updateDepartment(department, id), HttpStatus.OK);
    }
}
