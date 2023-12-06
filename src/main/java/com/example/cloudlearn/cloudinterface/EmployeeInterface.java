package com.example.cloudlearn.cloudinterface;

import java.util.List;

import com.example.cloudlearn.model.Employee;

public interface EmployeeInterface {
	
	List<Employee> getEmployees();

	Employee createEmployee(Employee employee);

	Employee updateEmployee(Long id, Employee updatedEmployee);

	void deleteEmployee(Long id);

}
