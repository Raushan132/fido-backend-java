package com.fido.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class VendorProduct {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(name="native",strategy = "native")
	private long id;
	private String title;
	private String description;
	private String price;
	private String rating;
	private String Stock;
	private String category;
	private String thumbnail;
	private String brand;
//	
//	@ManyToOne
//	@JoinColumn(name="vendor_id")
	private long prouductOwnerId;
	
	
}

