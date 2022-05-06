package com.endava.web.application.domain.service.dao;

import com.endava.web.application.domain.model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    DepartmentRepository repository;

    Department dep1;
    Department dep2;
    Department dep3;
    Department dep4;

    @BeforeEach

    void setUp() {
        dep1 = Department.builder().name("Administration").location("Chisinau").build();
        dep2 = Department.builder().name("Marketing").location("Amsterdam").build();
        dep3 = Department.builder().name("Human Resources").location("Berlin").build();
        dep4 = Department.builder().name("IT").location("Berlin").build();
        repository.save(dep1);
        repository.save(dep2);
        repository.save(dep3);
    }

    @Test
    public void shouldSaveDepartment() {
        Department department = repository.save(Department.builder().name("IT").location("Chisinau").build());

        assertThat(department).hasFieldOrPropertyWithValue("name", "IT");
        assertThat(department).hasFieldOrPropertyWithValue("location", "Chisinau");
    }

    @Test
    void shouldFindAllAllDepartments() {
        Iterable departments = repository.findAll();

        assertThat(departments).hasSize(3).contains(dep1, dep2, dep3);
    }

    @Test
    void shouldFindDepartmentById() {
        Department foundDepartment = repository.getById(dep2.getId());

        assertThat(foundDepartment).isEqualTo(dep2);
    }

    @Test
    void shouldUpdateDepartmentById() {
        Department departmentToUpdate = Department.builder().id(2).name("IT").location("Amsterdam").build();
        repository.save(departmentToUpdate);
        Department checkDepartment = repository.getById(dep2.getId());

        assertThat(checkDepartment.getId()).isEqualTo(dep2.getId());
        assertThat(checkDepartment.getName()).isEqualTo(departmentToUpdate.getName());
        assertThat(checkDepartment.getLocation()).isEqualTo(departmentToUpdate.getLocation());
    }

    @Test
    void shouldFindDepartmentByNameIfExistsAndReturnABoolean() {
        assertTrue(repository.existsByName(dep2.getName()));
        assertFalse(repository.existsByName(dep4.getName()));
    }
}