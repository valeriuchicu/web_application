package com.endava.web.application.domain.service;

import com.endava.web.application.domain.exception.exceptions.DepartmentDoesNotExistsException;
import com.endava.web.application.domain.exception.exceptions.EmployeeConstraintsException;
import com.endava.web.application.domain.exception.exceptions.NoSuchDepartmentException;
import com.endava.web.application.domain.exception.exceptions.NoSuchEmployeeException;
import com.endava.web.application.domain.model.Department;
import com.endava.web.application.domain.model.Employee;
import com.endava.web.application.domain.service.dao.DepartmentRepository;
import com.endava.web.application.domain.service.dao.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Autowired
    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Autowired
    ApplicationContext applicationContext;

    Employee employee1;
    Employee employee2;
    Employee employee3;
    List<Employee> employeeList;
    Department dep1;
    Department dep2;
    Department dep3;

    @BeforeEach
    void setUp() {
        dep1 = Department.builder().id(1).name("Administration").location("Chisinau").build();
        dep2 = Department.builder().id(2).name("Marketing").location("Chisinau").build();
        dep3 = Department.builder().id(3).name("Human Resources").location("Berlin").build();
        employeeService = new EmployeeService(employeeRepository);
        employee1 = Employee.builder()
                .id(1)
                .firstName("Steven")
                .lastName("King")
                .email("SKING")
                .phoneNumber("515.123.4567")
                .salary(24000)
                .department(dep1)
                .build();
        employee2 = Employee.builder()
                .id(2)
                .firstName("Neena")
                .lastName("Kochhar")
                .email("NKOCHHAR")
                .phoneNumber("515.123.4568")
                .salary(17000)
                .department(dep2)
                .build();
        employee3 = Employee.builder()
                .id(3)
                .firstName("Lex")
                .lastName("De Haan")
                .email("LDEHAAN")
                .phoneNumber("515.123.4569")
                .salary(9000)
                .department(dep3)
                .build();

        employeeList = Arrays.asList(employee1, employee2, employee3);
    }

    @Test
    void shouldSaveEmployee() {
        Employee employee = Employee.builder()
                .firstName("Bruce")
                .lastName("Ernst")
                .email("BERNST")
                .phoneNumber("590.423.4567")
                .salary(6000)
                .department(dep3)
                .build();
        when(employeeRepository.save(any())).thenReturn(employee);
        when(employeeRepository.existsByEmail(any())).thenReturn(false);
        when(employeeRepository.existsByPhoneNumber(any())).thenReturn(false);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        assertThat(savedEmployee).hasFieldOrPropertyWithValue("firstName", "Bruce");
        assertThat(savedEmployee).hasFieldOrPropertyWithValue("lastName", "Ernst");
        assertThat(savedEmployee).hasFieldOrPropertyWithValue("email", "BERNST");
        assertThat(savedEmployee).hasFieldOrPropertyWithValue("phoneNumber", "590.423.4567");
        assertThat(savedEmployee).hasFieldOrPropertyWithValue("salary", 6000.0);
        assertThat(savedEmployee).hasFieldOrPropertyWithValue("department", employee.getDepartment());
        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    void shouldFindAllAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> actualListOfEmployees = employeeService.getAllEmployees();

        assertThat(actualListOfEmployees).isEqualTo(employeeList);
        verify(employeeRepository,times(1)).findAll();
    }

    @Test
    void shouldFindEmployeeById() {
        when(employeeRepository.findById(2)).thenReturn(Optional.ofNullable(employee2));
        Employee foundEmployee = employeeService.getEmployeeById(2);

        assertThat(foundEmployee).isEqualTo(employee2);
        verify(employeeRepository, times(1)).findById(2);
    }

    @Test
    void shouldUpdateEmployeeById() {
        Employee employeeToUpdate = Employee.builder()
                .id(2)
                .firstName("NeenaNN")
                .lastName("KochharNN")
                .email("NKOCHHARNN")
                .phoneNumber("515.777.4555")
                .salary(55000)
                .department(dep1)
                .build();

        when(employeeRepository.getById(2)).thenReturn(employee2);
        when(employeeRepository.existsByEmail(any())).thenReturn(false);
        when(employeeRepository.existsByPhoneNumber(any())).thenReturn(false);
        when(employeeRepository.save(any())).thenReturn(employeeToUpdate);
        Employee checkEmployee = employeeService.updateEmployee(employeeToUpdate, employee2.getId());

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
                .phoneNumber("515.444.4555")
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
        when(employeeRepository.existsByEmail(any())).thenReturn(true);
        when(employeeRepository.existsByPhoneNumber(any())).thenReturn(true);
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
        when(employeeRepository.existsByEmail(any())).thenReturn(true);

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
        when(employeeRepository.existsByPhoneNumber(any())).thenReturn(true);
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
        when(employeeRepository.existsByEmail(any())).thenReturn(true);
        when(employeeRepository.getById(2)).thenReturn(employee2);
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
        when(employeeRepository.existsByPhoneNumber(any())).thenReturn(true);
        when(employeeRepository.getById(2)).thenReturn(employee2);
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
}