/*   */ package com.example.springbootmvnrest.exception;
/*   */ 
/*   */ public class EmployeeNotFoundException extends RuntimeException {
/*   */   public EmployeeNotFoundException(Integer id) {
/* 6 */     super("Could not find employee " + id);
/*   */   }
/*   */ }


/* Location:              D:\Technical\Java_J2EE\0Code_Repo\myRepo\springbootmvnrest\!\com\example\springbootmvnrest\exception\EmployeeNotFoundException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */