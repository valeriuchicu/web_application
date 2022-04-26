package com.endava.web_application.domain.service;

import com.endava.web_application.domain.dao.DepartmentRepository;
import com.endava.web_application.domain.dao.EmployeeRepository;
import com.endava.web_application.domain.model.Employee;
import com.endava.web_application.infrastructure.restcontroller.conversion.EmployeeConversion;
import com.endava.web_application.infrastructure.restcontroller.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;
    private final DepartmentRepository repositoryDepartment;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployee(int employee_id) {
        Optional<Employee> employee = repository.findById(employee_id);
        return employee.orElseThrow(() -> new NoSuchElementException(
                "EMPLOYEE WITH ID " + employee_id + " DOES NOT EXISTS"));
    }

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        ifEmailAndPhoneExists(employeeDto);
        if(!repositoryDepartment.findDepartmentById(employeeDto.getDepartment().getId()).isPresent()){
            throw new NoSuchElementException("Department with ID " + employeeDto.getDepartment().getId() + " does not exists");
        }

        Employee employee = repository.save(EmployeeConversion.toEmployee(employeeDto));
        return EmployeeConversion.from(employee);
    }

    public Employee updateEmployee(Employee employee) {
        ifEmailAndPhoneExists(employee);
        return repository.save(employee);
    }

    public void deleteEmployee(int employee_id) {
        repository.deleteById(employee_id);
    }

    private void ifEmailAndPhoneExists(Employee employee) {
        String email = Boolean.toString(repository.findEmployeeByEmail(employee.getEmail()).isPresent());
        String phone = Boolean.toString(repository.findEmployeeByPhoneNumber(employee.getPhoneNumber()).isPresent());
        switch(email + "|" + phone) {
            case "true|false":
                throw new NoSuchElementException("Employee with email " + employee.getEmail() + " already exists");
            case "false|true":
                throw new NoSuchElementException("Employee with phone number " + employee.getPhoneNumber() + " already exists");
            case "true|true":
                throw new NoSuchElementException("Employee with email " + employee.getEmail() + " and phone number " + employee.getPhoneNumber() + " already exists");
        }
    }
    private void ifEmailAndPhoneExists(EmployeeDto employeeDto) {
        String email = Boolean.toString(repository.findEmployeeByEmail(employeeDto.getEmail()).isPresent());
        String phone = Boolean.toString(repository.findEmployeeByPhoneNumber(employeeDto.getPhoneNumber()).isPresent());
        switch(email + "|" + phone) {
            case "true|false":
                throw new NoSuchElementException("Employee with email " + employeeDto.getEmail() + " already exists");
            case "false|true":
                throw new NoSuchElementException("Employee with phone number " + employeeDto.getPhoneNumber() + " already exists");
            case "true|true":
                throw new NoSuchElementException("Employee with email " + employeeDto.getEmail() + " and phone number " + employeeDto.getPhoneNumber() + " already exists");
        }
    }

}
