package com.endava.web_application.domain.dao;

import com.endava.web_application.domain.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    Optional<Department> findDepartmentById(int id);
}
