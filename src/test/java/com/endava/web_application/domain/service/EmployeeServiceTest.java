package com.endava.web_application.domain.service;

import com.endava.web_application.domain.dao.EmployeeRepository;
import com.endava.web_application.domain.model.Employee;
import com.endava.web_application.infrastructure.restcontroller.conversion.EmployeeConversion;
import com.endava.web_application.infrastructure.restcontroller.dto.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;
    @Mock
    EmployeeService employeeServiceMock;
    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;

    Employee employee;
    EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setFirstName("test employee");
        employeeDto = EmployeeConversion.from(employee);
    }

    @Test
    void getAllEmployees() {
    }

    @Test
    void getEmployee() {
    }

    @Test
    void saveEmployee_shouldReturnAUserWhenWeSaveAUser() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDto expected = employeeService.saveEmployee(employeeDto);

        assertThat(expected.getFirstName()).isSameAs(employeeDto.getFirstName());
    }

    @Test
    void updateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee expected = employeeService.updateEmployee(employee);

        assertThat(expected.getFirstName()).isEqualTo(employee.getFirstName());
    }

    @Test
    void deleteEmployee() {
        employeeServiceMock.deleteEmployee(1);
        verify(employeeServiceMock, times(1)).deleteEmployee(integerArgumentCaptor.capture());

        assertThat(1).isEqualTo(integerArgumentCaptor.getValue());
    }
}