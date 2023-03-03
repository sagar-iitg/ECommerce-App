/**
 * 
 */
package com.sk.validate;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author ksaga
 *
 */
public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {

	
	
	private Logger logger=LoggerFactory.getLogger(ImageNameValidator.class);
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		
		
		logger.info("Message from isValid:{}" ,value);
		
		//logic
		if(value.isBlank())
			return false;
		
		
		
		return true;
	}
	
	

}
