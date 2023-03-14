package com.fido.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class VendorProduct extends BaseEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(name="native",strategy = "native")
	private long id;
	@NotBlank(message="Invalid data")
	private String title;
	private String description;
	
	@NotBlank(message="Invalid data")
	private String price;
	private String rating;
	@NotBlank(message="Invalid data")
	private String Stock;
	@NotBlank(message="Invalid data")
	private String category;
	@NotBlank(message="Invalid data")
	private String thumbnail;
	private String brand;
	
	@NotBlank(message="Invalid data")
	@Column(columnDefinition = "varchar(3) default '18' ")
	private String gst;
	@JsonIgnore
	private String discount;
	
	
//	@ManyToOne
//	@JoinColumn(name="vendor_id")
	private long prouductOwnerId;
	
	
}

