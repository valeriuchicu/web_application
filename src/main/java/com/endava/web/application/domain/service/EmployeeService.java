package com.endava.web.application.domain.service;

import com.endava.web.application.domain.dao.DepartmentRepository;
import com.endava.web.application.domain.dao.EmployeeRepository;
import com.endava.web.application.domain.exception.exceptions.DepartmentConstraintsException;
import com.endava.web.application.domain.exception.exceptions.DepartmentDoesNotExistsException;
import com.endava.web.application.domain.exception.exceptions.NoSuchDepartmentException;
import com.endava.web.application.domain.exception.exceptions.NoSuchEmployeeException;
import com.endava.web.application.domain.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository repositoryDepartment;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(int employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return employee.orElseThrow(() -> new NoSuchEmployeeException(
                "EMPLOYEE WITH ID " + employeeId + " DOES NOT EXISTS"));
    }

    public Employee saveEmployee(Employee employee) {
        checkEmployeeNameConstraints(employee);
        if(!repositoryDepartment.findDepartmentById(employee.getDepartment().getId()).isPresent()){
            throw new NoSuchDepartmentException("DEPARTMENT WITH ID " + employee.getDepartment().getId() + " DOES NOT EXISTS");
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        if(!repositoryDepartment.existsById(employee.getDepartment().getId())){
           throw new DepartmentDoesNotExistsException("DEPARTMENT WITH THIS ID DOES NOT EXISTS");
        }
        checkEmployeeNameConstraints(employee);
        return employeeRepository.save(employee);
    }

    private void checkEmployeeNameConstraints(Employee employee){
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty() || employee.getFirstName().trim().isEmpty()) {
            throw new DepartmentConstraintsException("EMPLOYEE FIRST NAME CANNOT BE NULL, EMPTY OR BLANK");
        }
        if (employee.getLastName() == null || employee.getLastName().isEmpty() || employee.getLastName().trim().isEmpty()) {
            throw new DepartmentConstraintsException("EMPLOYEE LAST NAME CANNOT BE NULL, EMPTY OR BLANK");
        }
        if (employee.getEmail() == null || employee.getEmail().isEmpty() || employee.getEmail().trim().isEmpty()) {
            throw new DepartmentConstraintsException("EMPLOYEE EMAIL CANNOT BE NULL, EMPTY OR BLANK");
        }
        if (employee.getPhoneNumber() == null || employee.getPhoneNumber().isEmpty() || employee.getPhoneNumber().trim().isEmpty()) {
            throw new DepartmentConstraintsException("EMPLOYEE PHONE NUMBER CANNOT BE NULL, EMPTY OR BLANK");
        }
        ifEmailAndPhoneExists(employee);
        if (employee.getSalary() < 1.00) {
            throw new DepartmentConstraintsException("EMPLOYEE SALARY MUST BE >= 1.0");
        }
    }

    private void ifEmailAndPhoneExists(Employee employee) {
        String email = Boolean.toString(employeeRepository.existsByEmail(employee.getEmail()));
        String phone = Boolean.toString(employeeRepository.existsByPhoneNumber(employee.getPhoneNumber()));
        switch(email + "|" + phone) {
            case "true|false":
                throw new NoSuchEmployeeException("EMPLOYEE WITH EMAIL " + employee.getEmail() + " ALREADY EXISTS");
            case "false|true":
                throw new NoSuchEmployeeException("EMPLOYEE WITH PHONE NUMBER " + employee.getPhoneNumber() + " ALREADY EXISTS");
            case "true|true":
                throw new NoSuchEmployeeException("EMPLOYEE WITH EMAIL " + employee.getEmail() + " AND PHONE NUMBER " + employee.getPhoneNumber() + " ALREADY EXISTS");
        }
    }
}
