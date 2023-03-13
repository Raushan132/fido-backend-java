package com.fido.app.exception;

public class NotSufficientBalanceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6761208820296192382L;
	
	public NotSufficientBalanceException(String msg) {
		super(msg);
	}

}
