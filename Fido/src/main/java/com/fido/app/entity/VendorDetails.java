package com.fido.app.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class VendorDetails extends Details {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String urlBusinessDoc;
	
	@NotBlank(message = "Invalid Data")
	private String vendorCode;
	@NotBlank(message = "Invalid Data")
	private String gst;

}
