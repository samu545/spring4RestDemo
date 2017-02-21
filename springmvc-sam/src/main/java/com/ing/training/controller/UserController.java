package com.ing.training.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ing.training.domain.ErrorDetail;
import com.ing.training.domain.User;
import com.ing.training.service.UserManagementService;
import com.ing.training.validator.UserDataValidator;

/**
 * Handles requests for the application home page.
 * REST calls
 */
@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping(value = "/users")
public class UserController extends WebMvcConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserManagementService userService;

	@Autowired
	UserDataValidator userValidator;

	/** This method adds a new user having valid authorization
	 * @param user
	 * @return User and HttpStatus
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<? extends Object> addUser(@Valid @RequestBody User user, BindingResult bindingResult, Locale locale) {
		//userValidator.validate(user, bindingResult);
		if(bindingResult.hasErrors()){
			StringBuilder errorDetails=new StringBuilder();
			(bindingResult.getAllErrors()).stream().forEach(objectError-> errorDetails.append(objectError.toString()));
			logger.info("errorDetails: "+ errorDetails);
			ErrorDetail errorDetail=new ErrorDetail(HttpStatus.BAD_REQUEST.name(), errorDetails.toString());
			return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.BAD_REQUEST);
		}

		logger.info("user: " +user);
		User userInsert=userService.createUser(user, locale);
		logger.info("user inserted successfully: " +userInsert);
		return new ResponseEntity<User>(userInsert, HttpStatus.CREATED);
	}
	
	/** This method updates a existing user having valid authorization
	 * @param user
	 * @return User and HttpStatus
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<? extends Object> updateUser(@Valid @RequestBody User user, BindingResult bindingResult, Locale locale) {
		if(bindingResult.hasErrors()){
			StringBuilder errorDetails=new StringBuilder();
			(bindingResult.getAllErrors()).stream().forEach(objectError-> errorDetails.append(objectError.toString()));
			logger.info("errorDetails: "+ errorDetails);
			ErrorDetail errorDetail=new ErrorDetail(HttpStatus.BAD_REQUEST.name(), errorDetails.toString());
			return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.BAD_REQUEST);
		}
		logger.info("user: " +user);
		User userInsert=userService.updateUser(user, locale);
		logger.info("user updated successfully: " +userInsert);
		return new ResponseEntity<User>(userInsert, HttpStatus.CREATED);
	}

	/** Get user details by userId
	 * @param id
	 * @return User Details
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public User getUserById(@PathVariable int id) throws Exception{
		return userService.getUserById(id);
	}

	/** Get user details by userId
	 * @param id
	 * @return User Details
	 */	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<User> listUsers() {
		return userService.listUsers();
	}

}
