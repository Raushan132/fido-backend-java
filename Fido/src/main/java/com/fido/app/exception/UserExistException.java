package com.fido.app.exception;

public class UserExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7426619419598402793L;
    public UserExistException(String msg) {
    	super(msg);
    }
}
