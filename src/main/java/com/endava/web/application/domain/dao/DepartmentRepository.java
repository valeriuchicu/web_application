package com.endava.web.application.domain.dao;

import com.endava.web.application.domain.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findDepartmentById(int id);
    Optional<Department> findByName(String s);
}
