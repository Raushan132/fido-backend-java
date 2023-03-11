package com.fido.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;

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
	private String title;
	private String description;
	private String price;
	private String rating;
	private String Stock;
	private String category;
	private String thumbnail;
	private String brand;
	@Column(columnDefinition = "varchar(3) default '18' ")
	private String gst;
	@JsonIgnore
	private String discount;
	
	
//	@ManyToOne
//	@JoinColumn(name="vendor_id")
	private long prouductOwnerId;
	
	
}

