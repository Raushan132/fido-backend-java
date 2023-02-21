package com.fido.app.entity;

import javax.persistence.Entity;

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
	private String vendorCode;

}
