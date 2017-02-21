package com.ing.training.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ing.training.domain.ErrorDetail;


@ControllerAdvice
public class GlobalControllerAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<? extends Object> handleDataAccessException(DataAccessException dae)
	{
		logger.error("Database Error", dae);
		ErrorDetail errorDetail=new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.name(), "DataBase Error");
		return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<? extends Object> handleException(Exception dae)
	{
		logger.error("Error", dae);
		ErrorDetail errorDetail=new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.name(), "Error");
		return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
