package com.endava.web_application.domain.service;

import com.endava.web_application.domain.dao.DepartmentRepository;
import com.endava.web_application.domain.model.Department;
import com.endava.web_application.infrastructure.restcontroller.conversion.DepartmentConversion;
import com.endava.web_application.infrastructure.restcontroller.dto.DepartmentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;

    public List<Department> getAllDepartments() {
        return repository.findAll();
    }

    public Department getDepartment(int department_id) {
        Optional<Department> department = repository.findById(department_id);
        return department.orElseThrow(()-> new NoSuchElementException(
                "DEPARTMENT WITH ID " + department_id + " DOES NOT EXISTS"));
    }

    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = repository.save(DepartmentConversion.toDepartment(departmentDto));
        return DepartmentConversion.from(department);
    }

    public Department updateDepartment(Department department) {
        return repository.save(department);
    }

    public void deleteDepartment(int department_id) {
        repository.deleteById(department_id);
    }
}

