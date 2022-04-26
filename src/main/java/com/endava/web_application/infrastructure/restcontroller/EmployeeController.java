package com.endava.web_application.infrastructure.restcontroller;

import com.endava.web_application.domain.exception.EmployeeIncorrectData;
import com.endava.web_application.domain.model.Employee;
import com.endava.web_application.domain.service.EmployeeService;
import com.endava.web_application.infrastructure.restcontroller.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> showAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping("/employees/add")
    public ResponseEntity<EmployeeDto> addNewEmployee(@Validated @RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.saveEmployee(employeeDto), HttpStatus.OK);
    }

    @PutMapping("/employees/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.updateEmployee(employee), HttpStatus.OK);
    }

    @DeleteMapping("/employees/del/{id}")
    public String deleteEmployee(@PathVariable int id) {
        try {
            employeeService.deleteEmployee(id);
        }
        catch (Throwable t){
            throw new NoSuchElementException("EMPLOYEE WITH ID " + id + " DOES NOT EXISTS");
        }
        return "Employee with ID " + id + " was deleted";
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(NoSuchElementException exception){
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(Exception exception){
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());
        if(exception.getMessage().startsWith("Failed to convert")){
            data.setInfo("INSERTED VALUE IS NOT A NUMBER");
        }
        if(exception.getMessage().endsWith("XXX-XXX-XXXX]] ")){
            data.setInfo("For the phone number please use pattern XXX-XXX-XXXX");
        }
        if(exception.getMessage().endsWith("first name should contain at least two symbols]] ")){
            data.setInfo("The first name should contain at least two symbols");
        }
        if(exception.getMessage().endsWith("last name should contain at least two symbols]] ")){
            data.setInfo("The last name should contain at least two symbols");
        }
        if(exception.getMessage().endsWith("email cannot be empty]] ")){
            data.setInfo("The email cannot be empty");
        }
        if(exception.getMessage().endsWith(">= 1.0]] ")){
            data.setInfo("Salary must be >= 1.0");
        }
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
