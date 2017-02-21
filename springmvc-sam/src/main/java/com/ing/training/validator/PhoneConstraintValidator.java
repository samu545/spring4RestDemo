package com.ing.training.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {
	@Override
	public void initialize(Phone phone) {
		
	}
	@Override
	public boolean isValid(String phoneField, ConstraintValidatorContext ctx) {
		if(phoneField.isEmpty()) return false;
		return phoneField.matches("[0-9()-]*");
	}

}
