package com.endava.web_application.domain.dao;

import com.endava.web_application.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Optional<Employee> findEmployeeByEmail(String email);
    Optional<Employee> findEmployeeByPhoneNumber(String phoneNumber);
}
