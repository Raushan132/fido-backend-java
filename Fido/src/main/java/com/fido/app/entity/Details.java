package com.fido.app.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;

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
	
	
	
	private String mobile;
	private String altmobile;
	private String address;
	private String country;
	private String city;
	private String state;
	private String pincode;
	private String urlPanCard;
	private String urlAadhar;
	
	

}
