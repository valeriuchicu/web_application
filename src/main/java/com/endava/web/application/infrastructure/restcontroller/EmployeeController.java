package com.endava.web.application.infrastructure.restcontroller;

import com.endava.web.application.domain.model.Employee;
import com.endava.web.application.domain.service.EmployeeService;
import com.endava.web.application.infrastructure.restcontroller.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final Converter<EmployeeDto, Employee> employeeDtoEmployeeConverter;
    private final Converter<Employee, EmployeeDto> employeeEmployeeDtoConverter;

    @GetMapping("employees")
    public List<Employee> showAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("employees/{id}")
    public EmployeeDto getEmployee(@PathVariable int id) {
        return employeeEmployeeDtoConverter.convert(employeeService.getEmployee(id));
    }

    @PostMapping("employees")
    public ResponseEntity<EmployeeDto> addNewEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeDtoEmployeeConverter.convert(employeeDto);
        EmployeeDto employeeDtoToReturn = employeeEmployeeDtoConverter.convert(employeeService.saveEmployee(employee));
        return new ResponseEntity<>(employeeDtoToReturn, HttpStatus.OK);
    }

    @PutMapping("employees/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Integer id) {
        Employee employee = employeeDtoEmployeeConverter.convert(employeeDto);
        EmployeeDto employeeDtoToReturn = employeeEmployeeDtoConverter.convert(employeeService.updateEmployee(employee, id));
        return new ResponseEntity<>(employeeDtoToReturn, HttpStatus.OK);
    }
}
