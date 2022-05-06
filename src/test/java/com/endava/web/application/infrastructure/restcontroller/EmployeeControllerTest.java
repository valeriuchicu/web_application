package com.endava.web.application.infrastructure.restcontroller;

import com.endava.web.application.domain.model.Department;
import com.endava.web.application.domain.model.Employee;
import com.endava.web.application.domain.service.EmployeeService;
import com.endava.web.application.infrastructure.restcontroller.conversion.EmployeeDtoToEmployee;
import com.endava.web.application.infrastructure.restcontroller.conversion.EmployeeToEmployeeDto;
import com.endava.web.application.infrastructure.restcontroller.dto.EmployeeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private EmployeeDtoToEmployee employeeDtoToEmployee;
    @MockBean
    private EmployeeToEmployeeDto employeeToEmployeeDto;

    Employee employee1;
    Employee employee2;
    Employee employee3;
    Employee employee4;
    List<Employee> employees;
    Department dep1;
    Department dep2;

    @BeforeEach
    void setUp() {
        dep1 = Department.builder().name("Administration").location("Chisinau").build();
        dep2 = Department.builder().name("Marketing").location("Chisinau").build();
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
        employee4 = Employee.builder()
                .id(3)
                .firstName("Neena")
                .lastName("Kochhar")
                .email("NKOCHHAR")
                .phoneNumber("515.123.4568")
                .salary(17000)
                .department(dep2)
                .build();
        employees = Arrays.asList(employee1, employee2);
    }

    @Test
    public void shouldReturnAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Steven")))
                .andExpect(jsonPath("$[0].lastName", is("King")))
                .andExpect(jsonPath("$[0].department.name", is("Administration")))
                .andExpect(jsonPath("$[0].department.location", is("Chisinau")))
                .andExpect(jsonPath("$[0].email", is("SKING")))
                .andExpect(jsonPath("$[0].phoneNumber", is("515.123.4567")))
                .andExpect(jsonPath("$[0].salary", is(24000.0)))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Neena")))
                .andExpect(jsonPath("$[1].lastName", is("Kochhar")))
                .andExpect(jsonPath("$[1].department.name", is("Marketing")))
                .andExpect(jsonPath("$[1].department.location", is("Chisinau")))
                .andExpect(jsonPath("$[1].email", is("NKOCHHAR")))
                .andExpect(jsonPath("$[1].phoneNumber", is("515.123.4568")))
                .andExpect(jsonPath("$[1].salary", is(17000.0)))
                .andReturn();

        verify(employeeService, times(1)).getAllEmployees();
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    void shouldReturnEmployeeById() throws Exception {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(2)
                .firstName("Neena")
                .lastName("Kochhar")
                .email("NKOCHHAR")
                .phoneNumber("515.123.4568")
                .salary(17000.0)
                .department(dep2)
                .build();

        when(employeeService.getEmployeeById(2)).thenReturn(employee2);
        when(employeeToEmployeeDto.convert(employee2)).thenReturn(employeeDto);

        mockMvc.perform(get("/employees/{id}", 2))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.firstName", is("Neena")))
                .andExpect(jsonPath("$.lastName", is("Kochhar")))
                .andExpect(jsonPath("$.department.name", is("Marketing")))
                .andExpect(jsonPath("$.department.location", is("Chisinau")))
                .andExpect(jsonPath("$.email", is("NKOCHHAR")))
                .andExpect(jsonPath("$.phoneNumber", is("515.123.4568")))
                .andExpect(jsonPath("$.salary", is(17000.0)))
                .andReturn();

        verify(employeeService, times(1)).getEmployeeById(2);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    void shouldSaveEmployee() throws Exception {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(4)
                .firstName("NeenaADD")
                .lastName("KochharADD")
                .email("NKOCHHARADD")
                .phoneNumber("515.123.4444")
                .salary(77000.0)
                .department(dep2)
                .build();

        Employee returnedEmployee = Employee.builder()
                .id(4)
                .firstName("NeenaADD")
                .lastName("KochharADD")
                .email("NKOCHHARADD")
                .phoneNumber("515.123.4444")
                .salary(77000.0)
                .department(dep2)
                .build();
        when(employeeToEmployeeDto.convert(returnedEmployee)).thenReturn(employeeDto);
        when(employeeDtoToEmployee.convert(employeeDto)).thenReturn(returnedEmployee);
        when(employeeService.saveEmployee(returnedEmployee)).thenReturn(returnedEmployee);
        String content = objectMapper.writeValueAsString(employeeDto);

        mockMvc.perform(post("/employees")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.firstName", is("NeenaADD")))
                .andExpect(jsonPath("$.lastName", is("KochharADD")))
                .andExpect(jsonPath("$.department.name", is("Marketing")))
                .andExpect(jsonPath("$.department.location", is("Chisinau")))
                .andExpect(jsonPath("$.email", is("NKOCHHARADD")))
                .andExpect(jsonPath("$.phoneNumber", is("515.123.4444")))
                .andExpect(jsonPath("$.salary", is(77000.0)))
                .andReturn();
    }

    @Test
    void shouldUpdateDepartment() throws Exception {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(3)
                .firstName("Neena")
                .lastName("Kochhar")
                .email("NKOCHHAR")
                .phoneNumber("515.123.4568")
                .salary(17000.0)
                .department(dep2)
                .build();

        Employee employeeToUpdate = Employee.builder()
                .id(3)
                .firstName("Neena")
                .lastName("Kochhar")
                .email("NKOCHHAR")
                .phoneNumber("515.123.4568")
                .salary(17000)
                .department(dep2)
                .build();
        when(employeeDtoToEmployee.convert(employeeDto)).thenReturn(employeeToUpdate);
        when(employeeToEmployeeDto.convert(employeeToUpdate)).thenReturn(employeeDto);
        when(employeeService.updateEmployee(employeeToUpdate, 3)).thenReturn(employeeToUpdate);
        String content = objectMapper.writeValueAsString(employeeDto);

        mockMvc.perform(put("/employees/3")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.firstName", is("Neena")))
                .andExpect(jsonPath("$.lastName", is("Kochhar")))
                .andExpect(jsonPath("$.department.name", is("Marketing")))
                .andExpect(jsonPath("$.department.location", is("Chisinau")))
                .andExpect(jsonPath("$.email", is("NKOCHHAR")))
                .andExpect(jsonPath("$.phoneNumber", is("515.123.4568")))
                .andExpect(jsonPath("$.salary", is(17000.0)))
                .andReturn();
    }
}