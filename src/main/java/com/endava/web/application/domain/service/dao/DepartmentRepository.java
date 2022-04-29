package com.endava.web.application.domain.service.dao;

import com.endava.web.application.domain.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByName(String name);
    Optional<Department> findByName(String name);
}
