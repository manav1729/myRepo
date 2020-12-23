package com.example.springbootmvnrest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_EMPLOYEES")
public class Employee {
  private Integer id;
  
  private String firstName;
  
  private String lastName;
  
  private String emailId;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
    return this.id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  @Column(name = "first_name", nullable = false)
  public String getFirstName() {
    return this.firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  @Column(name = "last_name", nullable = false)
  public String getLastName() {
    return this.lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  @Column(name = "email")
  public String getEmailId() {
    return this.emailId;
  }
  
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }
  
  public String toString() {
    return "Employee{id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' + ", emailId='" + this.emailId + '\'' + '}';
  }
}
