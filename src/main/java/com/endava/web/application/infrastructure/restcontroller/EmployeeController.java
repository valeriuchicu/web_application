package com.endava.web.application.infrastructure.restcontroller;

import com.endava.web.application.domain.model.Employee;
import com.endava.web.application.domain.service.EmployeeService;
import com.endava.web.application.infrastructure.restcontroller.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final Converter<EmployeeDto, com.endava.web.application.domain.model.Employee> employeeDtoEmployeeConverter;
    private final Converter<com.endava.web.application.domain.model.Employee, EmployeeDto> employeeEmployeeDtoConverter;

    @GetMapping("employees")
    public List<Employee> showAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("employees/{id}")
    public EmployeeDto getEmployee(@PathVariable int id) {
        return employeeEmployeeDtoConverter.convert(employeeService.getEmployee(id));
    }

    @PostMapping("employees")
    public ResponseEntity<EmployeeDto> addNewEmployee(@Validated @RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeEmployeeDtoConverter.convert(
                employeeService.saveEmployee(employeeDtoEmployeeConverter.convert(employeeDto)))
                , HttpStatus.OK);
    }

    @PutMapping("employees/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@Validated @RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeEmployeeDtoConverter.convert(
                employeeService.updateEmployee(employeeDtoEmployeeConverter.convert(employeeDto)))
                , HttpStatus.OK);
    }
}
