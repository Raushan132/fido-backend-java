package com.fido.app.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@PrimaryKeyJoinColumn(name = "detailId")
public class Details extends User_Vendor_Auth {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@NotBlank(message = "Invalid Data")
	private String mobile;
	private String altmobile;
	
	@NotBlank(message = "Invalid Data")
	private String address;
	
	@NotBlank(message = "Invalid Data")
	private String country;
	
	@NotBlank(message = "Invalid Data")
	private String city;
	
	@NotBlank(message = "Invalid Data")
	private String state;
	
	@NotBlank(message = "Invalid Data")
	private String pincode;
	private String urlPanCard;
	private String urlAadhar;
	

}
