package com.spring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Big picture: We’re going to create a simple payroll service that manages the employees of a company. 
 * We’ll store employee objects in a (H2 in-memory) database, and access them (via something called JPA). 
 * Then we’ll wrap that with something that will allow access over the internet (called the Spring MVC layer).
 */
@SpringBootApplication
public class DemoMaven11Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoMaven11Application.class, args);
	}

}
