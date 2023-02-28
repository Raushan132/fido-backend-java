package com.fido.app.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(name="native",strategy = "native")
	private long invoiceId;
	private String panNo;
	private String gst;
	private String orderNO;
	private String orderDate;
	private String invoiceNo;
	private String invoiceDate;
	private String v_address;
	private String v_email;
	private String v_state;
	private String v_city;
	private String v_country;
	private String c_address;
	private String c_state;
	private String c_city;
	private String c_country;
	private String c_email;
	
	
	@OneToMany
	@JoinColumn(name="invoice_prduct")
	private List<InvoiceProduct> product;
	
	
	

}
