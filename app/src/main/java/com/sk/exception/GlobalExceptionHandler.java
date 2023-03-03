package com.sk.exception;

/**
* @author
* Sagar Kumar
*/

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sk.dtos.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	
	//handler resource not found exception
	private Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		
		
		logger.info("Exception hanldler invoked");
		
		ApiResponseMessage response=ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
		
		return  new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		
		
		
		
		
		
		
	}
	
	
	//MethodArgumentNotValidException
	@ExceptionHandler(MethodArgumentNotValidException.class) 
	public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		List<ObjectError> allErros=ex.getBindingResult().getAllErrors();
		
		Map<String, Object> response=new HashMap<>();
		
		allErros.stream().forEach(objectError->{
			String msg=objectError.getDefaultMessage();
			String field=((FieldError) objectError).getField();
		
			response.put(field, msg);
			
			
		});
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		
	}
	

	
	
	//handle bad Api request exception
	
	
	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequestException ex){
		
		
		logger.info("Bad api request");
		
		ApiResponseMessage response=ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
		
		return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		
		
		
	}
	
	
	
	
}
