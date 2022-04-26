package com.endava.web_application.infrastructure.restcontroller;

import com.endava.web_application.domain.exception.DepartmentIncorrectData;
import com.endava.web_application.domain.model.Department;
import com.endava.web_application.domain.service.DepartmentService;
import com.endava.web_application.infrastructure.restcontroller.dto.DepartmentDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/departments")
    public List<Department> showAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/department/{id}")
    public Department getDepartment(@PathVariable int id) {

        return departmentService.getDepartment(id);
    }

    @PostMapping("/departments/add")
    public ResponseEntity<DepartmentDto> addNewDepartment(@Validated @RequestBody DepartmentDto departmentDto) {
        return new ResponseEntity<>(departmentService.saveDepartment(departmentDto), HttpStatus.OK);
    }

    @PutMapping("/departments/update")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
        return new ResponseEntity<>(departmentService.updateDepartment(department), HttpStatus.OK);
    }

    @DeleteMapping("/departments/del/{id}")
    public String deleteDepartment(@PathVariable int id) {
        try {
            departmentService.deleteDepartment(id);
        }
        catch (Throwable t){
            throw new NoSuchElementException("DEPARTMENT WITH ID " + id + " DOES NOT EXISTS");
        }
        return "Department with ID " + id + " was deleted";
    }

    @ExceptionHandler
    public ResponseEntity<DepartmentIncorrectData> handleException(
            NoSuchElementException exception){
        DepartmentIncorrectData data = new DepartmentIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DepartmentIncorrectData> handleException(
            Exception exception){
        DepartmentIncorrectData data = new DepartmentIncorrectData();
        data.setInfo(exception.getMessage());
        if(exception.getMessage().startsWith("Failed to convert")){
            data.setInfo("INSERTED VALUE IS NOT A NUMBER");
        }
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}














