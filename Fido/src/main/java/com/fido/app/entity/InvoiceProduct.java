package com.fido.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class InvoiceProduct {
 
	@Id
	@GeneratedValue
	private long invoiceProductId;
	private String title;
	private String description;
	private String price;
	private String qty;
	private String discount;
	private String tax;
	private String totalamt;
	

}
