package com.endava.web.application.domain.dao;

import com.endava.web.application.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

}
