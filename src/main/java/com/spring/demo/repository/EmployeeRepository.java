package com.spring.demo.repository;

import java.util.List;

import com.spring.demo.entity.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	/* Definition of custom finder method/Query Method
	 * The implementation is plugged in by Spring Data JPA automatically
	 */
	List<Employee> findByNameContaining(String name); //returns all Employees whose name contains input name

	// Sorting with Spring Data JPA: method derivation using the OrderBy keyword
	List<Employee> findAllByOrderByIdAsc();
	List<Employee> findAllByOrderByIdDesc();

	// Sorting with Spring Data JPA: using Sort object as method parameter specifying property name(s) and direction
	List<Employee> findByRoleContaining(String role, Sort sort); 
}
