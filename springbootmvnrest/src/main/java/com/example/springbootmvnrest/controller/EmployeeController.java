package com.example.springbootmvnrest.controller;

import com.example.springbootmvnrest.entity.Employee;
import com.example.springbootmvnrest.exception.EmployeeNotFoundException;
import com.example.springbootmvnrest.service.EmployeeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/v1"})
public class EmployeeController {
  @Autowired
  private final EmployeeService employeeService;
  
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }
  
  @GetMapping({"/employees"})
  public List<Employee> getAllEmployees() {
    System.out.println("Inside EmployeeController.getAllEmployees.");
    return this.employeeService.findAll();
  }
  
  @GetMapping({"/employees/{id}"})
  public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer employeeId) throws EmployeeNotFoundException {
    Employee employee = (Employee)this.employeeService.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    return ResponseEntity.ok().body(employee);
  }
  
  @PostMapping({"/employees"})
  public Employee createEmployee(@Valid @RequestBody Employee employee) {
    return this.employeeService.save(employee);
  }
  
  @PutMapping({"/employees/{id}"})
  public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer employeeId, @Valid @RequestBody Employee employeeDetails) throws EmployeeNotFoundException {
    Employee employee = (Employee)this.employeeService.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    employee.setEmailId(employeeDetails.getEmailId());
    employee.setLastName(employeeDetails.getLastName());
    employee.setFirstName(employeeDetails.getFirstName());
    Employee updatedEmployee = this.employeeService.save(employee);
    return ResponseEntity.ok(updatedEmployee);
  }
  
  @DeleteMapping({"/employees/{id}"})
  public Map<String, Boolean> deleteEmployee(@PathVariable("id") Integer employeeId) throws EmployeeNotFoundException {
    Employee employee = (Employee)this.employeeService.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    this.employeeService.delete(employee);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
