package com.example.cloudlearn.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloudlearn.exception.EmployeeException.EmployeeNotFoundException;
import com.example.cloudlearn.model.Employee;
import com.example.cloudlearn.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Employee Management")
@RestController
@RequestMapping("/api")
public class EmployeeRequestController {
	
	private static final Logger logger = LogManager.getLogger(EmployeeRequestController.class);

	@Autowired
	EmployeeService employeeService;

	@ApiOperation(value = "Get a list of all employees")
	@GetMapping("/getAll")
	public ResponseEntity<List<Employee>> getEmployeeApi() {
		try {
			List<Employee> employees = employeeService.getEmployees();
			if (employees != null && !employees.isEmpty()) {
				return ResponseEntity.ok(employees);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			logger.error("Error occurred: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@ApiOperation(value = "Create a new employee")
	@PostMapping("/create")
	public ResponseEntity<Employee> postEmployeeApi(@RequestBody Employee employee) {
		try {
			return ResponseEntity.ok(employeeService.createEmployee(employee));
		} catch (Exception e) {
			logger.error("Error occurred: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@ApiOperation(value = "Update an existing employee by ID")
	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> putEmployeeApi(@PathVariable Long id, @RequestBody Employee employee) {
		try {
			return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
		} catch (EmployeeNotFoundException e) {
			logger.error("Error occurred: ", e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			logger.error("Error occurred: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
	
	@ApiOperation(value = "Delete an employee by ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployeeApi(@PathVariable Long id) {
	    try {
	        employeeService.deleteEmployee(id);
	        return ResponseEntity.ok("Employee deleted succesfully"); // Return 204 No Content on successful deletion
	    } catch (EmployeeNotFoundException e) {
	    	logger.error("Error occurred: ", e);
	        return ResponseEntity.notFound().build(); // Return 404 Not Found if the employee is not found
	    } catch (Exception e) {
	    	logger.error("Error occurred: ", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


}
