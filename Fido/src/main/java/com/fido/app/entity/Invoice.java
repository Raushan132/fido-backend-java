package com.fido.app.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	private String orderNo;
	private String orderDate;
	private String invoiceNo;
	private String invoiceDate;
	private String v_address;
	private String v_email;
	private String v_state;
	private String v_city;
	private String v_country;
	
	@JsonProperty("v_Mob")
	private String vMob;
	@JsonProperty("v_fullName")
	private String vFullName;
	private String c_address;
	private String c_state;
	private String c_city;
	private String c_country;
	
	@JsonProperty("c_fullName")
	private String cFullName;
	@JsonProperty("c_Mob")
	private String cMob;
	@JsonProperty("c_email")
	private String cEmail;
	
	private String grandTotal;
	
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name="invoice_products",joinColumns = @JoinColumn(name="ids",referencedColumnName = "invoiceId"),inverseJoinColumns = @JoinColumn(name="productId",referencedColumnName = "invoiceProductId") )
	private List<InvoiceProduct> product;
	
	
	

}
