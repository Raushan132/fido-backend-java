package com.fido.app.model;

import lombok.Data;

@Data
public class Invoice {
	
	private String panNo;
	private String gst;
	private String orderNO;
	private String orderDate;
	private String invoiceNo;
	private String invoiceDate;
	private String vaddress;
	private String vstate;
	private String vcity;
	private String vcountry;
	
	

}
