package com.ing.training.exception;

public class NoUserFound extends RuntimeException {
	
	/**
	 * samrajT
	 */
	private static final long serialVersionUID = 1L;

	public NoUserFound(String msg, Throwable cause){
		super(msg, cause);
	}

}
