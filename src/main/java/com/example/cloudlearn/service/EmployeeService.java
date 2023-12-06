package com.example.cloudlearn.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cloudlearn.cloudinterface.EmployeeInterface;
import com.example.cloudlearn.entity.EmployeeEntity;
import com.example.cloudlearn.exception.EmployeeException.EmployeeNotFoundException;
import com.example.cloudlearn.model.Employee;
import com.example.cloudlearn.repository.EmployeeRepository;

@Service
public class EmployeeService implements EmployeeInterface {

	private static final Logger logger = LogManager.getLogger(EmployeeService.class);

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ModelMapper modelMapper;

	/**
	 * Retrieve all existing employees.
	 * 
	 * @return A list of employee DTOs.
	 */
	@Override
	public List<Employee> getEmployees() {
		// Retrieve all existing employees
		List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

		// Map the fetched entity list to a DTO (Employee) list and return it
		return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity, Employee.class))
				.collect(Collectors.toList());
	}

	/**
	 * Create a new employee.
	 * 
	 * @param employee The employee DTO to be created.
	 * @return The created employee DTO.
	 */
	@Override
	public Employee createEmployee(Employee employee) {
		EmployeeEntity employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
		EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);
		logger.info("Employee created successfully");

		// Map the saved entity back to a DTO (Employee) and return it
		return modelMapper.map(savedEntity, Employee.class);
	}

	/**
	 * Update an existing employee.
	 * 
	 * @param id              The ID of the employee to update.
	 * @param updatedEmployee The updated employee DTO.
	 * @return The updated employee DTO.
	 * @throws EmployeeNotFoundException If the specified employee is not found.
	 */
	@Override
	public Employee updateEmployee(Long id, Employee updatedEmployee) {
		// Retrieve the existing employee entity by its ID
		Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(id);

		if (optionalEmployeeEntity.isPresent()) {
			EmployeeEntity existingEmployeeEntity = optionalEmployeeEntity.get();
			updatedEmployee.setId(existingEmployeeEntity.getId());

			// Map the fields from the updatedEmployee to the existingEmployeeEntity
			modelMapper.map(updatedEmployee, existingEmployeeEntity);

			// Save the updated entity
			EmployeeEntity updatedEntity = employeeRepository.save(existingEmployeeEntity);
			logger.info("Employee updated successfully");
			
			// Map the updated entity back to a DTO (Employee) and return it
			return modelMapper.map(updatedEntity, Employee.class);
		} else {
			// Handle the case when the employee with the specified ID is not found
			throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
		}
	}

	/**
	 * Delete an employee by ID.
	 * 
	 * @param id The ID of the employee to delete.
	 * @return A ResponseEntity with an appropriate HTTP status code. - 204 No
	 *         Content: Successful deletion. - 404 Not Found: If the employee with
	 *         the specified ID is not found. - 500 Internal Server Error: In case
	 *         of an unexpected error.
	 */
	@Override
	public void deleteEmployee(Long id) {
		Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(id);

		if (optionalEmployeeEntity.isPresent()) {
			employeeRepository.deleteById(id);
			logger.info("Employee deleted successfully");
		} else {
			throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
		}
	}

}
