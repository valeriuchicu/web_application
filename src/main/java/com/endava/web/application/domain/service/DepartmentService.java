package com.endava.web.application.domain.service;

import com.endava.web.application.domain.dao.DepartmentRepository;
import com.endava.web.application.domain.exception.exceptions.DepartmentAlreadyExistsException;
import com.endava.web.application.domain.exception.exceptions.DepartmentConstraintsException;
import com.endava.web.application.domain.exception.exceptions.NoSuchDepartmentException;
import com.endava.web.application.domain.model.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;

    public List<Department> getAllDepartments() {
        return repository.findAll();
    }

    public Department getDepartment(int departmentId) {
        Optional<Department> department = repository.findById(departmentId);
        return department.orElseThrow(()-> new NoSuchDepartmentException(
                "DEPARTMENT WITH ID " + departmentId + " DOES NOT EXISTS"));
    }

    public Department saveDepartment(Department department) {
        checkDepartmentConstraints(department);
        checkIfDepartmentWithSuchNameExists(department);
        return repository.save(department);
    }

    public Department updateDepartment(Department department) {
        checkDepartmentConstraints(department);
        checkIfDepartmentWithSuchNameExists(department);
        return repository.save(department);
    }

    private void checkIfDepartmentWithSuchNameExists(Department department){
        repository.findByName(department.getName()).ifPresent(department1 -> {
            throw new DepartmentAlreadyExistsException("DEPARTMENT WITH THIS NAME ALREADY EXISTS");
        });
    }

    private void checkDepartmentConstraints(Department department){
        if (department.getName() == null || department.getName().isEmpty() || department.getName().trim().isEmpty()) {
            throw new DepartmentConstraintsException("DEPARTMENT NAME CANNOT BE NULL, EMPTY OR BLANK");
        }
        if (department.getLocation() == null || department.getLocation().isEmpty() || department.getLocation().trim().isEmpty()) {
            throw new DepartmentConstraintsException("DEPARTMENT LOCATION CANNOT BE NULL, EMPTY OR BLANK");
        }
    }
}
