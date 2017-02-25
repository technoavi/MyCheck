package com.techsource.mycheck.utility;

public class MyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6658222689246089711L;

	public MyException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyException(String msg) {
	}

}
