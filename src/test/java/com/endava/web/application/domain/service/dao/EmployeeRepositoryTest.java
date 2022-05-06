package com.endava.web.application.domain.service.dao;

import com.endava.web.application.domain.exception.exceptions.DepartmentDoesNotExistsException;
import com.endava.web.application.domain.exception.exceptions.EmployeeConstraintsException;
import com.endava.web.application.domain.exception.exceptions.NoSuchDepartmentException;
import com.endava.web.application.domain.exception.exceptions.NoSuchEmployeeException;
import com.endava.web.application.domain.model.Department;
import com.endava.web.application.domain.model.Employee;
import com.endava.web.application.domain.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    Employee employee1;
    Employee employee2;
    Employee employee3;
    Employee employee4;
    EmployeeService employeeService;
    Department dep1;
    Department dep2;
    Department dep3;

    @BeforeEach
    void setUp() {
        dep1 = Department.builder().name("Administration").location("Chisinau").build();
        dep2 = Department.builder().name("Marketing").location("Chisinau").build();
        dep3 = Department.builder().name("Human Resources").location("Berlin").build();
        employeeService = new EmployeeService(employeeRepository);
        employee1 = Employee.builder()
                .firstName("Steven")
                .lastName("King")
                .email("SKING")
                .phoneNumber("515.123.4567")
                .salary(24000)
                .department(dep1)
                .build();
        employee2 = Employee.builder()
                .firstName("Neena")
                .lastName("Kochhar")
                .email("NKOCHHAR")
                .phoneNumber("515.123.4568")
                .salary(17000)
                .department(dep2)
                .build();
        employee3 = Employee.builder()
                .firstName("Lex")
                .lastName("De Haan")
                .email("LDEHAAN")
                .phoneNumber("515.123.4569")
                .salary(9000)
                .department(dep3)
                .build();
        employee4 = Employee.builder()
                .email("LDEHAANNNNNN")
                .phoneNumber("515.123.4589")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
    }

    @Test
    void shouldSaveEmployee() {
        Employee employee = employeeService.saveEmployee(Employee.builder()
                .firstName("Bruce")
                .lastName("Ernst")
                .email("BERNST")
                .phoneNumber("590.423.4568")
                .salary(6000)
                .department(dep3)
                .build());
        assertThat(employee).hasFieldOrPropertyWithValue("firstName", "Bruce");
        assertThat(employee).hasFieldOrPropertyWithValue("lastName", "Ernst");
        assertThat(employee).hasFieldOrPropertyWithValue("email", "BERNST");
        assertThat(employee).hasFieldOrPropertyWithValue("phoneNumber", "590.423.4568");
        assertThat(employee).hasFieldOrPropertyWithValue("salary", 6000.0);
        assertThat(employee).hasFieldOrPropertyWithValue("department", employee.getDepartment());
    }

    @Test
    void shouldFindAllAllEmployees() {
        Iterable employees = employeeService.getAllEmployees();

        assertThat(employees).hasSize(3).contains(employee1, employee2, employee3);
    }

    @Test
    void shouldFindEmployeeById() {
        Employee foundEmployee = employeeService.getEmployeeById(employee2.getId());

        assertThat(foundEmployee).isEqualTo(employee2);
    }

    @Test
    void shouldUpdateEmployeeById() {
        Employee employeeToUpdate = Employee.builder()
                .firstName("NeenaNN")
                .lastName("KochharNN")
                .email("NKOCHHARNN")
                .phoneNumber("515.777.4568")
                .salary(55000)
                .department(dep1)
                .build();
        employeeService.updateEmployee(employeeToUpdate, employee2.getId());
        Employee checkEmployee = employeeService.getEmployeeById(employee2.getId());

        assertThat(checkEmployee.getId()).isEqualTo(employee2.getId());
        assertThat(checkEmployee.getFirstName()).isEqualTo(employeeToUpdate.getFirstName());
        assertThat(checkEmployee.getLastName()).isEqualTo(employeeToUpdate.getLastName());
        assertThat(checkEmployee.getEmail()).isEqualTo(employeeToUpdate.getEmail());
        assertThat(checkEmployee.getPhoneNumber()).isEqualTo(employeeToUpdate.getPhoneNumber());
        assertThat(checkEmployee.getSalary()).isEqualTo(employeeToUpdate.getSalary());
        assertThat(checkEmployee.getDepartment()).isEqualTo(employeeToUpdate.getDepartment());
    }

    @Test
    void shouldThrowNoSuchDepartmentExceptionWhenWeTryToSaveAnEmployeeWithNonExistingDepartment() {
        Department departmentWhichDoesNotExists = Department.builder().name("Administration").location("Amsterdam").build();
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kochha")
                .email("NKOCHHA")
                .phoneNumber("515.444.4568")
                .salary(33000)
                .department(departmentWhichDoesNotExists)
                .build();

        assertThatExceptionOfType(NoSuchDepartmentException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("DEPARTMENT WITH ID " + employee.getDepartment().getId() + " DOES NOT EXISTS");
    }

    @Test
    void shouldThrowDepartmentDoesNotExistsExceptionWhenWeTryToUpdateAnEmployeeWithNonExistingDepartment() {
        Department departmentWhichDoesNotExists = Department.builder().name("Administration").location("Amsterdam").build();
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kochha")
                .email("NKOCHHA")
                .phoneNumber("515.444.4568")
                .salary(33000)
                .department(departmentWhichDoesNotExists)
                .build();

        assertThatExceptionOfType(DepartmentDoesNotExistsException.class)
                .isThrownBy(() -> employeeService.updateEmployee(employee, employee.getId()))
                .withMessage("DEPARTMENT WITH THIS ID DOES NOT EXISTS");
    }

    @Test
    void shouldThrowEmployeeConstraintsExceptionIfEmployeeFirstNameIsNull() {
        Employee employee = Employee.builder()
                .firstName(null)
                .lastName("Kochha")
                .email("NKOCHHA")
                .phoneNumber("515.444.4568")
                .salary(33000)
                .department(null)
                .build();

        assertThatExceptionOfType(EmployeeConstraintsException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE FIRST NAME CANNOT BE NULL, EMPTY OR BLANK");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldThrowEmployeeConstraintsExceptionIfEmployeeFirstNameIsEmptyOrBlank(String firstName) {
        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName("Kochha")
                .email("NKOCHHA")
                .phoneNumber("515.444.4568")
                .salary(33000)
                .department(null)
                .build();

        assertThatExceptionOfType(EmployeeConstraintsException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE FIRST NAME CANNOT BE NULL, EMPTY OR BLANK");
    }

    @Test
    void shouldThrowEmployeeConstraintsExceptionIfEmployeeLastNameIsNull() {
        Employee employee = Employee.builder()
                .firstName("Kocha")
                .lastName(null)
                .email("NKOCHHA")
                .phoneNumber("515.444.4568")
                .salary(33000)
                .department(null)
                .build();

        assertThatExceptionOfType(EmployeeConstraintsException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE LAST NAME CANNOT BE NULL, EMPTY OR BLANK");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldThrowEmployeeConstraintsExceptionIfEmployeeLastNameIsEmptyOrBlank(String lastName) {
        Employee employee = Employee.builder()
                .firstName("Kochha")
                .lastName(lastName)
                .email("NKOCHHA")
                .phoneNumber("515.444.4568")
                .salary(33000)
                .department(null)
                .build();

        assertThatExceptionOfType(EmployeeConstraintsException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE LAST NAME CANNOT BE NULL, EMPTY OR BLANK");
    }

    @Test
    void shouldThrowEmployeeConstraintsExceptionIfEmployeeAEmailIsNull() {
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kocha")
                .email(null)
                .phoneNumber("515.444.4568")
                .salary(33000)
                .department(null)
                .build();

        assertThatExceptionOfType(EmployeeConstraintsException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE EMAIL CANNOT BE NULL, EMPTY OR BLANK");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldThrowEmployeeConstraintsExceptionIfEmployeeEmailIsEmptyOrBlank(String email) {
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kochha")
                .email(email)
                .phoneNumber("515.444.4568")
                .salary(33000)
                .department(null)
                .build();

        assertThatExceptionOfType(EmployeeConstraintsException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE EMAIL CANNOT BE NULL, EMPTY OR BLANK");
    }

    @Test
    void shouldThrowEmployeeConstraintsExceptionIfEmployeePhoneNumberIsNull() {
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kocha")
                .email("NKOCHHA")
                .phoneNumber(null)
                .salary(33000)
                .department(null)
                .build();

        assertThatExceptionOfType(EmployeeConstraintsException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE PHONE NUMBER CANNOT BE NULL, EMPTY OR BLANK");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldThrowEmployeeConstraintsExceptionIfEmployeePhoneNumberIsEmptyOrBlank(String phoneNumber) {
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kochha")
                .email("NKOCHHA")
                .phoneNumber(phoneNumber)
                .salary(33000)
                .department(null)
                .build();

        assertThatExceptionOfType(EmployeeConstraintsException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE PHONE NUMBER CANNOT BE NULL, EMPTY OR BLANK");
    }

    @Test
    void shouldThrowEmployeeConstraintsExceptionIfEmployeeSalaryIsEmptyOrBlank() {
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kochha")
                .email("NKOCHHA")
                .phoneNumber("515.444.4568")
                .salary(0.5)
                .department(null)
                .build();

        assertThatExceptionOfType(EmployeeConstraintsException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE SALARY MUST BE >= 1.0");
    }

    @Test
    void shouldThrowEmployeeConstraintsExceptionIfEmployeeEmailAndPhoneNumberAlreadyExists() {
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kochha")
                .email("SKING")
                .phoneNumber("515.123.4567")
                .salary(24000)
                .department(null)
                .build();

        assertThatExceptionOfType(NoSuchEmployeeException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE WITH EMAIL " + employee.getEmail() + " AND PHONE NUMBER " + employee.getPhoneNumber() + " ALREADY EXISTS");
    }

    @Test
    void shouldThrowEmployeeConstraintsExceptionIfEmployeeEmailAlreadyExists() {
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kochha")
                .email("SKING")
                .phoneNumber("555.123.4567")
                .salary(24000)
                .department(null)
                .build();

        assertThatExceptionOfType(NoSuchEmployeeException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE WITH EMAIL " + employee.getEmail() + " ALREADY EXISTS");
    }

    @Test
    void shouldThrowEmployeeConstraintsExceptionIfEmployeePhoneNumberAlreadyExists() {
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kochha")
                .email("SKINGS")
                .phoneNumber("515.123.4567")
                .salary(24000)
                .department(null)
                .build();

        assertThatExceptionOfType(NoSuchEmployeeException.class)
                .isThrownBy(() -> employeeService.saveEmployee(employee))
                .withMessage("EMPLOYEE WITH PHONE NUMBER " + employee.getPhoneNumber() + " ALREADY EXISTS");
    }

    @Test
    void shouldThrowEmployeeConstraintsExceptionIfEmployeeEmailAlreadyExistsWhenTryToUpdate() {
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kochha")
                .email("SKING")
                .phoneNumber("555.123.4567")
                .salary(24000)
                .department(null)
                .build();

        assertThatExceptionOfType(EmployeeConstraintsException.class)
                .isThrownBy(() -> employeeService.updateEmployee(employee, employee2.getId()))
                .withMessage("EMPLOYEE WITH EMAIL " + employee.getEmail() + " ALREADY EXISTS");
    }

    @Test
    void shouldThrowEmployeeConstraintsExceptionIfEmployeePhoneNumberAlreadyExistsWhenTryToUpdate() {
        Employee employee = Employee.builder()
                .firstName("Neen")
                .lastName("Kochha")
                .email("SKINGS")
                .phoneNumber("515.123.4567")
                .salary(24000)
                .department(null)
                .build();

        assertThatExceptionOfType(EmployeeConstraintsException.class)
                .isThrownBy(() -> employeeService.updateEmployee(employee, employee2.getId()))
                .withMessage("EMPLOYEE WITH PHONE NUMBER " + employee.getPhoneNumber() + " ALREADY EXISTS");
    }

    @Test
    void shouldFindByEmailEmployeeIfItExists() {
        assertTrue(employeeRepository.existsByEmail(employee2.getEmail()));
        assertFalse(employeeRepository.existsByEmail(employee4.getEmail()));
    }

    @Test
    void shouldFindByPhoneNumberEmployeeIfItExists() {
        assertTrue(employeeRepository.existsByPhoneNumber(employee2.getPhoneNumber()));
        assertFalse(employeeRepository.existsByPhoneNumber(employee4.getPhoneNumber()));
    }
}