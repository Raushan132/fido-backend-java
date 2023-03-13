package com.fido.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data

public class InvoiceProduct {
 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(name="native", strategy = "native")
	private long invoiceProductId;
	private String title;
	private String description;
	private String price;
	private String qty;
	private String discount;
	private String tax;
	private String totalamt;
	

}
