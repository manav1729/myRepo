package com.example.springbootmvnrest.repository;

import com.example.springbootmvnrest.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {}


/* Location:              D:\Technical\Java_J2EE\0Code_Repo\myRepo\springbootmvnrest\!\com\example\springbootmvnrest\repository\EmployeeRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */