package com.spring.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.spring.demo.exception.EmployeeInvalidSortParameterException;
import com.spring.demo.repository.EmployeeRepository;
import com.spring.demo.entity.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeSortController {

	private final EmployeeRepository empRepo;

	//EmployeeRepository is injected by constructor into EmployeeController
	EmployeeSortController(EmployeeRepository empRepo) {
		this.empRepo = empRepo; 
	}

	/* /employees/sort : sort by [id,asc] : default
	 * /employees/sort?sort=name,asc : sort by [name,asc]
	 * /employees/sort?sort=name,asc&sort=id,desc : sort by [name,asc] then sort by [id,desc]
	 * 	EmployeeInvalidSortParameterException when value for query param 'sort' is other than 'asc' or 'desc'
	 * Query params with same name but with different values maps to String[]
	 * When no query param is not available but default value "id,asc" is present
	 * 	 String[] sort has values {"id", "asc"}
	 * When query param is present as ?sort=name,asc
	 * 	 String[] sort has values {"name", "asc"}
	 * When query param is present with multiple values as ?sort=name,asc&sort=id,desc
	 * 	 String[] sort has values {"name,asc", "id,desc"}
	 */
	@GetMapping("/employees/sort")
	public ResponseEntity<List<Employee>> findSortedEmployees(
			@RequestParam(defaultValue="id,asc", required=false) String[] sort) {
		List<Employee> sortedEmployees = new ArrayList<Employee>();
		List<Order> orders = new ArrayList<Order>();

		if(sort[0].contains(",")) {
			for(String str : sort) {
				String[] _sort = str.split(",");
				switch (_sort[1]) {
				case "asc":
					orders.add(new Order(Sort.Direction.ASC, _sort[0]));
					break;
				case "desc":
					orders.add(new Order(Sort.Direction.DESC, _sort[0]));
					break;
				default:
					String msg = "Invalid Value: " + sort + " (Accpted values: asc, desc)";
					throw new EmployeeInvalidSortParameterException(msg);
				}	
			}
		}
		else {
			switch (sort[1]) {
			case "asc":
				orders.add(new Order(Sort.Direction.ASC, sort[0]));
				break;
			case "desc":
				orders.add(new Order(Sort.Direction.DESC, sort[0]));
				break;
			default:
				String msg = "Invalid Value: " + sort + " (Accpted values: asc, desc)";
				throw new EmployeeInvalidSortParameterException(msg);
			}	
		}

		sortedEmployees = empRepo.findAll(Sort.by(orders));
		if (sortedEmployees.isEmpty())
			return (new ResponseEntity<>(HttpStatus.NO_CONTENT));
		return (new ResponseEntity<>(sortedEmployees, HttpStatus.OK));
	}

	/* /employees/role?role=ro&sort=asc : employees with role like '%ro%' and sort by [role,asc]
	 * /employees/role?role=ro&sort=desc : employees with role like '%ro%' and sort by [role,desc]
	 * 	EmployeeInvalidSortParameterException when value for query param 'sort' is other than 'asc' or 'desc'
	 */
	@GetMapping("/employees/role")
	public ResponseEntity<List<Employee>> findEmployeesByRoleContaining(
			@RequestParam(value="role") String role, 
			@RequestParam(value="sort") String sort) {
		List<Employee> sortedEmployees = new ArrayList<Employee>();
		List<Order> orders = new ArrayList<Order>();

		switch (sort) {
		case "asc":
			orders.add(new Order(Sort.Direction.ASC, "role"));
			break;
		case "desc":
			orders.add(new Order(Sort.Direction.DESC, "role"));
			break;
		default:
			String msg = "Invalid Value: " + sort + " (Accpted values: asc, desc)";
			throw new EmployeeInvalidSortParameterException(msg);
		}	

		sortedEmployees = empRepo.findByRoleContaining(role, Sort.by(orders));
		if (sortedEmployees.isEmpty()) {
			return (new ResponseEntity<>(HttpStatus.NO_CONTENT));
		}
		return (new ResponseEntity<>(sortedEmployees, HttpStatus.OK));
	}

	@GetMapping("/employee/role/nameDesc") 
	public ResponseEntity<List<Employee>> allSortedEmployeesByRoleAscAndNameDesc() {

		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Sort.Direction.ASC, "role"));
		orders.add(new Order(Sort.Direction.DESC, "name"));

		List<Employee> employees = empRepo.findAll(Sort.by(orders));
		if (employees.isEmpty())
			return (new ResponseEntity<>(HttpStatus.NO_CONTENT));
		return (new ResponseEntity<>(employees, HttpStatus.OK));
	}

	@GetMapping("/employee/name/roleDesc")
	public ResponseEntity<List<Employee>> allSortedEmployeesByNameAscAndRoleDesc() {
		List<Employee> employees = empRepo.findAll(Sort.by("name").and(Sort.by("role").descending()));
		if (employees.isEmpty())
			return (new ResponseEntity<>(HttpStatus.NO_CONTENT));
		return (new ResponseEntity<>(employees, HttpStatus.OK));
	}

	@GetMapping("/employee/name/desc")
	public ResponseEntity<List<Employee>> allSortedEmployeesByNameDesc() {
		List<Employee> employees = empRepo.findAll(Sort.by("name").descending());
		if (employees.isEmpty())
			return (new ResponseEntity<>(HttpStatus.NO_CONTENT));
		return (new ResponseEntity<>(employees, HttpStatus.OK));
	}

	/* /employee : order by [id,asc] : default
	 * /employee?sort=asc : order by [id,asc]
	 * /employee?sort=desc : order by [id,desc]
	 * 	EmployeeInvalidSortParameterException when value for query param 'sort' is other than 'asc' or 'desc'
	 */
	@GetMapping("/employee")
	public ResponseEntity<List<Employee>> allSortedEmployees(
			@RequestParam(value="sort", defaultValue="asc", required=false) String sort) {
		List<Employee> sortedEmployees = new ArrayList<Employee>();

		if(StringUtils.hasLength(sort)) {
			switch (sort) {
			case "asc":
				sortedEmployees = empRepo.findAllByOrderByIdAsc();
				break;
			case "desc":
				sortedEmployees = empRepo.findAllByOrderByIdDesc();
				break;
			default:
				String msg = "Invalid Value: " + sort + " (Accpted values: asc, desc)";
				throw new EmployeeInvalidSortParameterException(msg);
			}	
		}
		else
			sortedEmployees = empRepo.findAllByOrderByIdAsc();

		if (sortedEmployees.isEmpty())
			return (new ResponseEntity<>(HttpStatus.NO_CONTENT));
		return (new ResponseEntity<>(sortedEmployees, HttpStatus.OK));
	}

}
