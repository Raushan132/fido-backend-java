package com.fido.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User_Detail  {
	
	
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO, generator = "native")
	@GenericGenerator(name="native", strategy = "native")
	private long id;
	private String fullName;
	private String address;
	private String gender;
	private String mobile;
	private String dob;
	private String country;
	private String postalcode;
	private String region;
	private String language;
	
	


}
