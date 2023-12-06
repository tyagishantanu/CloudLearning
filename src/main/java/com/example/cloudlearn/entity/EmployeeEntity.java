package com.example.cloudlearn.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Employee")
public class EmployeeEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name = "name")
    private String name;
    
	@Column(name = "company")
    private String company;
    
	@Column(name = "address")
    private String address;
    
	@Column(name = "age")
    private Integer age;

}
