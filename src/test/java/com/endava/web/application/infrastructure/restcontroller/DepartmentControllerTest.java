package com.endava.web.application.infrastructure.restcontroller;

import com.endava.web.application.domain.model.Department;
import com.endava.web.application.domain.service.DepartmentService;
import com.endava.web.application.infrastructure.restcontroller.conversion.DepartmentDtoToDepartment;
import com.endava.web.application.infrastructure.restcontroller.conversion.DepartmentToDepartmentDto;
import com.endava.web.application.infrastructure.restcontroller.dto.DepartmentDto;
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

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private DepartmentService departmentService;
    @MockBean
    private DepartmentDtoToDepartment departmentDtoToDepartmentConverter;
    @MockBean
    private DepartmentToDepartmentDto departmentToDepartmentDtoConverter;

    Department dep1;
    Department dep2;
    Department dep3;
    Department dep4;
    DepartmentDto depDto3;
    DepartmentDto depDto4;
    List<Department> departments;

    @BeforeEach
    void setUp() {
        dep1 = Department.builder().id(1).name("Administration").location("Chisinau").build();
        dep2 = Department.builder().id(2).name("Marketing").location("Amsterdam").build();
        dep3 = Department.builder().id(3).name("Human Resources").location("Berlin").build();
        dep4 = Department.builder().id(3).name("Human Resources").location("Berlin").build();
        depDto4 = new DepartmentDto(3, "Human Resources", "Berlin");
        depDto3 = new DepartmentDto(3, "Human Resources", "Berlin");
        departments = Arrays.asList(dep1, dep2);
    }

    @Test
    public void shouldReturnAllDepartments() throws Exception {
        when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/departments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Administration")))
                .andExpect(jsonPath("$[0].location", is("Chisinau")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Marketing")))
                .andExpect(jsonPath("$[1].location", is("Amsterdam")))
                .andReturn();

        verify(departmentService, times(1)).getAllDepartments();
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    void shouldReturnDepartmentById() throws Exception {
        when(departmentService.getDepartmentById(2)).thenReturn(dep2);

        mockMvc.perform(get("/departments/{id}", 2))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Marketing")))
                .andExpect(jsonPath("$.location", is("Amsterdam")))
                .andReturn();

        verify(departmentService, times(1)).getDepartmentById(2);
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    void shouldSaveDepartment() throws Exception {
        when(departmentDtoToDepartmentConverter.convert(depDto3)).thenReturn(dep3);
        when(departmentToDepartmentDtoConverter.convert(dep4)).thenReturn(depDto3);
        when(departmentService.saveDepartment(dep3)).thenReturn(dep3);
        String content = objectMapper.writeValueAsString(dep3);

        mockMvc.perform(post("/departments")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("Human Resources")))
                .andExpect(jsonPath("$.location", is("Berlin")))
                .andReturn();
    }

    @Test
    void shouldUpdateDepartment() throws Exception {
        Department departmentToUpdate = Department.builder().id(1).name("IT").location("Bon").build();
        DepartmentDto departmentDtoToUpdate = DepartmentDto.builder().id(1).name("IT").location("Bon").build();
        when(departmentDtoToDepartmentConverter.convert(departmentDtoToUpdate)).thenReturn(departmentToUpdate);
        when(departmentToDepartmentDtoConverter.convert(departmentToUpdate)).thenReturn(departmentDtoToUpdate);
        when(departmentService.updateDepartment(departmentToUpdate, departmentToUpdate.getId())).thenReturn(departmentToUpdate);
        String content = objectMapper.writeValueAsString(departmentToUpdate);

        mockMvc.perform(put("/departments/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("IT")))
                .andExpect(jsonPath("$.location", is("Bon")))
                .andReturn();
    }
}
