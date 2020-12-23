/*    */ package com.example.springbootmvnrest.service;
/*    */ 
/*    */ import com.example.springbootmvnrest.entity.Employee;
/*    */ import com.example.springbootmvnrest.repository.EmployeeRepository;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class EmployeeService {
/*    */   @Autowired
/*    */   private final EmployeeRepository employeeRepository;
/*    */   
/*    */   public EmployeeService(EmployeeRepository employeeRepository) {
/* 17 */     this.employeeRepository = employeeRepository;
/*    */   }
/*    */   
/*    */   public List<Employee> findAll() {
/* 21 */     System.out.println("Inside EmployeeService.findAll.");
/* 22 */     return this.employeeRepository.findAll();
/*    */   }
/*    */   
/*    */   public Optional<Employee> findById(Integer employeeId) {
/* 26 */     return this.employeeRepository.findById(employeeId);
/*    */   }
/*    */   
/*    */   public Employee save(Employee employee) {
/* 30 */     return (Employee)this.employeeRepository.save(employee);
/*    */   }
/*    */   
/*    */   public void delete(Employee employee) {
/* 34 */     this.employeeRepository.delete(employee);
/*    */   }
/*    */ }


/* Location:              D:\Technical\Java_J2EE\0Code_Repo\myRepo\springbootmvnrest\!\com\example\springbootmvnrest\service\EmployeeService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */