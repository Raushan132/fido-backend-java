package com.fido.app.exception;

public class CardExpireException extends Exception {
      /**
	 *   If Card is Expire then throw Card Expire Exception
	 */
	private static final long serialVersionUID = 4881944140843262004L;

	public CardExpireException(String msg) {
    	  super(msg);
      }
}
