package com.endava.web.application.infrastructure.restcontroller;

import com.endava.web.application.domain.model.Department;
import com.endava.web.application.domain.service.DepartmentService;
import com.endava.web.application.infrastructure.restcontroller.dto.DepartmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final Converter<DepartmentDto, Department> departmentDtoToDepartmentConverter;
    private final Converter<Department, DepartmentDto> departmentToDepartmentDtoConverter;

    @GetMapping("/departments")
    public List<Department> showAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/departments/{id}")
    public Department getDepartment(@PathVariable int id) {

        return departmentService.getDepartmentById(id);
    }

    @PostMapping("/departments")
    public ResponseEntity<DepartmentDto> addNewDepartment(@RequestBody DepartmentDto departmentDto) {
        final Department department = departmentDtoToDepartmentConverter.convert(departmentDto);
        final DepartmentDto dtoDepartment = departmentToDepartmentDtoConverter
                .convert(departmentService.saveDepartment(department));

        return new ResponseEntity<>(dtoDepartment, HttpStatus.OK);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDto departmentDto, @PathVariable int id) {
        final Department department = departmentDtoToDepartmentConverter.convert(departmentDto);
        final DepartmentDto dtoDepartment = departmentToDepartmentDtoConverter
                .convert(departmentService.updateDepartment(department, id));
        return new ResponseEntity<>(dtoDepartment, HttpStatus.OK);
    }
}