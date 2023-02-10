package com.spring.demo.controller;

import java.util.List;

import com.spring.demo.exception.EmployeeNotFoundException;
import com.spring.demo.repository.EmployeeRepository;
import com.spring.demo.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	private final EmployeeRepository empRepo;

	//EmployeeRepository is injected by constructor into EmployeeController
	EmployeeController(EmployeeRepository empRepo) {
		this.empRepo = empRepo; 
	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> allEmployees() {
		List<Employee> employees = empRepo.findAll();
		if (employees.isEmpty())
			return (new ResponseEntity<>(HttpStatus.NO_CONTENT));
		return (new ResponseEntity<>(employees, HttpStatus.OK));
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
		/* EmployeeNotFoundException to indicate when an employee is looked up but not found
		 *  using orElseThrow() method of java.util.Optional 
		 *    If value is present in java.util.Optional then returns value otherwise throws the exception
		 */
		Employee employee = empRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		return (new ResponseEntity<>(employee, HttpStatus.OK));
	}

	@GetMapping("/employees/name/{name}")
	public ResponseEntity<List<Employee>> findByNameContaining(@PathVariable("name") String name) {
		List<Employee> employees = empRepo.findByNameContaining(name);
		if (employees.isEmpty())
			return (new ResponseEntity<>(HttpStatus.NO_CONTENT));
		return (new ResponseEntity<>(employees, HttpStatus.OK));
	}

	@PostMapping("/employees")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee newEmployee) {
		Employee employee = empRepo.save(newEmployee);
		return (new ResponseEntity<>(employee, HttpStatus.CREATED));
	}

	@DeleteMapping("/employees")
	public ResponseEntity<HttpStatus> deleteAllEmployees() {
		empRepo.deleteAll();
		return (new ResponseEntity<>(HttpStatus.NO_CONTENT));
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable Long id) {
		empRepo.deleteById(id);
		return (new ResponseEntity<>(HttpStatus.NO_CONTENT));
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee newEmployee, @PathVariable Long id) {
		Employee employee = empRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		employee.setName(newEmployee.getName()); 
		employee.setRole(newEmployee.getRole());
		return (new ResponseEntity<>(empRepo.save(employee), HttpStatus.OK));
	} 

}
