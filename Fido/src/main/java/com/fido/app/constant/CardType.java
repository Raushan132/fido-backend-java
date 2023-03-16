package com.fido.app.constant;

public enum CardType {
	GOLD("10000"),PLATINUM("15000"),DIAMOND("25000");
	
	private final String amt;
	CardType(String amt) {
		// TODO Auto-generated constructor stub
		this.amt=amt;
		
	}
	public String getCardAmount() {
		return this.amt;
	}
}
