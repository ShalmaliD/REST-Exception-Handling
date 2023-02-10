package com.spring.demo.config;

import com.spring.demo.repository.EmployeeRepository;
import com.spring.demo.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	/* Spring Boot will run ALL CommandLineRunner beans once the application context is loaded
	 * This runner will request a copy of EmployeeRepository
	 * Using it, it will create below entities and store them
	 */
	@Bean
	CommandLineRunner initDatabase(EmployeeRepository empRepo) {
		return args -> {
			log.info("Preloading " + empRepo.save(new Employee("Bilbo Baggins", "burglar")));
			log.info("Preloading " + empRepo.save(new Employee("Frodo Baggins", "thief")));
			log.info("Preloading " + empRepo.save(new Employee("James Bond", "police")));
			log.info("Preloading " + empRepo.save(new Employee("Batman", "superhero")));
			log.info("Preloading " + empRepo.save(new Employee("Joker", "joker")));
			log.info("Preloading " + empRepo.save(new Employee("Mr Beans", "comedian")));
			log.info("Preloading " + empRepo.save(new Employee("Superman", "superhero")));
			log.info("Preloading " + empRepo.save(new Employee("Powerpuff Girls", "cartoon characters")));
			log.info("Preloading " + empRepo.save(new Employee("Joker", "philosopher")));
		};
	}

}
