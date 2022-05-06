package com.endava.web.application.domain.service;

import com.endava.web.application.domain.exception.exceptions.DepartmentAlreadyExistsException;
import com.endava.web.application.domain.exception.exceptions.DepartmentConstraintsException;
import com.endava.web.application.domain.model.Department;
import com.endava.web.application.domain.service.dao.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    DepartmentRepository repository;

    @InjectMocks
    DepartmentService departmentService;

    Department dep1;
    Department dep2;
    Department dep3;
    List<Department> departmentList;

    @BeforeEach
    void setUp() {
        departmentList = new ArrayList<>();
        dep1 = Department.builder().id(1).name("Administration").location("Chisinau").build();
        dep2 = Department.builder().id(2).name("Marketing").location("Amsterdam").build();
        dep3 = Department.builder().id(3).name("Human Resources").location("Berlin").build();

        departmentList.add(dep1);
        departmentList.add(dep2);
        departmentList.add(dep3);
    }

    @Test
    public void shouldSaveDepartment() {
        Department departmentRepo = Department.builder().name("IT").location("Chisinau").build();

        when(repository.save(any())).thenReturn(departmentRepo);
        Department department = departmentService.saveDepartment(departmentRepo);

        assertThat(department).hasFieldOrPropertyWithValue("name", "IT");
        assertThat(department).hasFieldOrPropertyWithValue("location", "Chisinau");
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldFindAllAllDepartments() {
        when(repository.findAll()).thenReturn(departmentList);
        List<Department> departmentList1 =departmentService.getAllDepartments();

        assertEquals(departmentList1, departmentList);
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldFindDepartmentById() {
        when(repository.findById(2)).thenReturn(Optional.ofNullable(dep2));
        Department department = departmentService.getDepartmentById(2);

        assertThat(department).isEqualTo(dep2);
        verify(repository, times(1)).findById(2);
    }

    @Test
    void shouldUpdateDepartmentById() {
        Department departmentToUpdate = Department.builder().id(2).name("IT").location("Amsterdam").build();
        when(repository.getById(2)).thenReturn(dep2);
        when(repository.save(departmentToUpdate)).thenReturn(departmentToUpdate);
        Department checkDepartment = departmentService.updateDepartment(departmentToUpdate, dep2.getId());

        assertThat(checkDepartment).hasFieldOrPropertyWithValue("name", "IT");
        assertThat(checkDepartment).hasFieldOrPropertyWithValue("location", "Amsterdam");
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldThrowDepartmentConstraintsExceptionIfNameOfDepartmentAlreadyExists(){
        Department departmentToUpdate = Department.builder().name("Administration").location("Amsterdam").build();
        when(repository.existsByName(departmentToUpdate.getName())).thenReturn(true);
        when(repository.getById(2)).thenReturn(dep2);

        assertThatExceptionOfType(DepartmentConstraintsException.class)
                .isThrownBy(() -> departmentService.updateDepartment(departmentToUpdate, dep2.getId()))
                .withMessage("DEPARTMENT WITH NAME " + departmentToUpdate.getName() + " ALREADY EXISTS");
    }

    @Test
    void shouldThrowDepartmentAlreadyExistsExceptionIfDepartmentWithSuchNameAlreadyExists(){
        Department departmentToUpdate = Department.builder().name("Administration").location("Amsterdam").build();
        when(repository.existsByName(departmentToUpdate.getName())).thenReturn(true);

        assertThatExceptionOfType(DepartmentAlreadyExistsException.class)
                .isThrownBy(() -> departmentService.saveDepartment(departmentToUpdate))
                .withMessage("DEPARTMENT WITH THIS NAME ALREADY EXISTS");
    }

    @Test
    void shouldThrowDepartmentConstraintsExceptionIfDepartmentNameIsNull(){
        Department departmentToUpdate = Department.builder().name(null).location("Amsterdam").build();

        assertThatExceptionOfType(DepartmentConstraintsException.class)
                .isThrownBy(() -> departmentService.saveDepartment(departmentToUpdate))
                .withMessage("DEPARTMENT NAME CANNOT BE NULL, EMPTY OR BLANK");
    }

    @Test
    void shouldThrowDepartmentConstraintsExceptionIfDepartmentNameIsEmpty(){
        Department departmentToUpdate = Department.builder().name("").location("Amsterdam").build();

        assertThatExceptionOfType(DepartmentConstraintsException.class)
                .isThrownBy(() -> departmentService.saveDepartment(departmentToUpdate))
                .withMessage("DEPARTMENT NAME CANNOT BE NULL, EMPTY OR BLANK");
    }

    @Test
    void shouldThrowDepartmentConstraintsExceptionIfDepartmentNameIsBlank(){
        Department departmentToUpdate = Department.builder().name("    ").location("Amsterdam").build();

        assertThatExceptionOfType(DepartmentConstraintsException.class)
                .isThrownBy(() -> departmentService.saveDepartment(departmentToUpdate))
                .withMessage("DEPARTMENT NAME CANNOT BE NULL, EMPTY OR BLANK");
    }

    @Test
    void shouldThrowDepartmentConstraintsExceptionIfDepartmentLocationIsNull(){
        Department departmentToUpdate = Department.builder().name("Administration").location(null).build();

        assertThatExceptionOfType(DepartmentConstraintsException.class)
                .isThrownBy(() -> departmentService.saveDepartment(departmentToUpdate))
                .withMessage("DEPARTMENT LOCATION CANNOT BE NULL, EMPTY OR BLANK");
    }

    @Test
    void shouldThrowDepartmentConstraintsExceptionIfDepartmentLocationIsEmpty(){
        Department departmentToUpdate = Department.builder().name("Administration").location("").build();

        assertThatExceptionOfType(DepartmentConstraintsException.class)
                .isThrownBy(() -> departmentService.saveDepartment(departmentToUpdate))
                .withMessage("DEPARTMENT LOCATION CANNOT BE NULL, EMPTY OR BLANK");
    }

    @Test
    void shouldThrowDepartmentConstraintsExceptionIfDepartmentLocationIsBlanck(){
        Department departmentToUpdate = Department.builder().name("Administration").location("  ").build();

        assertThatExceptionOfType(DepartmentConstraintsException.class)
                .isThrownBy(() -> departmentService.saveDepartment(departmentToUpdate))
                .withMessage("DEPARTMENT LOCATION CANNOT BE NULL, EMPTY OR BLANK");
    }
}