package com.endava.web.application.domain.service;

import com.endava.web.application.domain.service.dao.DepartmentRepository;
import com.endava.web.application.domain.service.dao.EmployeeRepository;
import com.endava.web.application.domain.exception.exceptions.*;
import com.endava.web.application.domain.model.Employee;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
        ifEmailAndPhoneExists(employee);
        validateEmployeeNameConstraints(employee);
        if (employee.getDepartment() != null
                && ! repositoryDepartment.findById(employee.getDepartment().getId()).isPresent() ) {
            throw new NoSuchDepartmentException("DEPARTMENT WITH ID " + employee.getDepartment().getId() + " DOES NOT EXISTS");
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee, Integer id) {
        if (!repositoryDepartment.existsById(employee.getDepartment().getId())) {
            throw new DepartmentDoesNotExistsException("DEPARTMENT WITH THIS ID DOES NOT EXISTS");
        }
        validateEmployeeNameConstraints(employee);

        Employee employeeFromDB = employeeRepository.getById(id);
        if (!Objects.equals(employee.getEmail(), employeeFromDB.getEmail())) {
            verifyEmail(employee);
            employeeFromDB.setEmail(employee.getEmail());
        }
        if (!Objects.equals(employee.getPhoneNumber(), employeeFromDB.getPhoneNumber())) {
            verifyPhoneNumber(employee);
            employeeFromDB.setPhoneNumber(employee.getPhoneNumber());
        }
        employeeFromDB.setFirstName(employee.getFirstName());
        employeeFromDB.setLastName(employee.getLastName());
        employeeFromDB.setSalary(employee.getSalary());

        return employeeRepository.save(employeeFromDB);
    }

    private void validateEmployeeNameConstraints(Employee employee) {
        if (StringUtils.isBlank(employee.getFirstName())) {
            throw new DepartmentConstraintsException("EMPLOYEE FIRST NAME CANNOT BE NULL, EMPTY OR BLANK");
        }
        if (StringUtils.isBlank(employee.getLastName())) {
            throw new DepartmentConstraintsException("EMPLOYEE LAST NAME CANNOT BE NULL, EMPTY OR BLANK");
        }
        if (StringUtils.isBlank(employee.getEmail())) {
            throw new DepartmentConstraintsException("EMPLOYEE EMAIL CANNOT BE NULL, EMPTY OR BLANK");
        }
        if (StringUtils.isBlank(employee.getPhoneNumber())) {
            throw new DepartmentConstraintsException("EMPLOYEE PHONE NUMBER CANNOT BE NULL, EMPTY OR BLANK");
        }
        if (employee.getSalary() < 1.00) {
            throw new DepartmentConstraintsException("EMPLOYEE SALARY MUST BE >= 1.0");
        }
    }

    private void ifEmailAndPhoneExists(Employee employee) {
        boolean email = employeeRepository.existsByEmail(employee.getEmail());
        boolean phone = employeeRepository.existsByPhoneNumber(employee.getPhoneNumber());
        if (email && phone) {
            throw new NoSuchEmployeeException("EMPLOYEE WITH EMAIL " + employee.getEmail() + " AND PHONE NUMBER " + employee.getPhoneNumber() + " ALREADY EXISTS");
        }
        if (email) {
            throw new NoSuchEmployeeException("EMPLOYEE WITH EMAIL " + employee.getEmail() + " ALREADY EXISTS");
        }
        if (phone) {
            throw new NoSuchEmployeeException("EMPLOYEE WITH PHONE NUMBER " + employee.getPhoneNumber() + " ALREADY EXISTS");
        }
    }

    protected void verifyEmail(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new EmployeeConstraintsException("EMPLOYEE WITH EMAIL " + employee.getEmail() + " ALREADY EXISTS");
        }
    }

    protected void verifyPhoneNumber(Employee employee) {
        if (employeeRepository.existsByPhoneNumber(employee.getPhoneNumber())) {
            throw new EmployeeConstraintsException("EMPLOYEE WITH EMAIL " + employee.getPhoneNumber() + " ALREADY EXISTS");
        }
    }
}
