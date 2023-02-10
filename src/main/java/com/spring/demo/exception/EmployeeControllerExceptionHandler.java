package com.spring.demo.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/* Spring supports exception handling by a global Exception Handler (@ExceptionHandler) with Controller Advice (@ControllerAdvice)
 * This enables a mechanism that makes ResponseEntity work with the type safety and flexibility of @ExceptionHandler
 * When EmployeeNotFoundException is thrown 
 *  then EmployeeNotFoundAdvice (Spring MVC configuration) is used to render HTTP 404
 */
@ControllerAdvice
public class EmployeeControllerExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<EmployeeExceptionMessage> employeeNotFoundExceptionHandler(EmployeeNotFoundException ex, WebRequest request) {
		//EmployeeExceptionMessage(int statusCode, Date timestamp, String message, String description)
		EmployeeExceptionMessage message = 
				new EmployeeExceptionMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmployeeInvalidSortParameterException.class)
	public ResponseEntity<EmployeeExceptionMessage> employeeInvalidSortParameterExceptionHandler(EmployeeInvalidSortParameterException ex, WebRequest request) {
		//EmployeeExceptionMessage(int statusCode, Date timestamp, String message, String description)
		EmployeeExceptionMessage message = 
				new EmployeeExceptionMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<EmployeeExceptionMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		//EmployeeExceptionMessage(int statusCode, Date timestamp, String message, String description)
		EmployeeExceptionMessage message = 
				new EmployeeExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
