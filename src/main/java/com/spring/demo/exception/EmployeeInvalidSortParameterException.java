package com.spring.demo.exception;

public class EmployeeInvalidSortParameterException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EmployeeInvalidSortParameterException(String msg) {
		super(msg);
	}

}
