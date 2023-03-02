package com.fido.app.model;

import java.util.List;

import com.fido.app.entity.VendorProduct;

import lombok.Data;

@Data
public class Vendors_Products {
     
	private String email;
	private String fullName;
	private List<VendorProduct> product;
	
	public void setVendorsProdData(String email,String fullName,List<VendorProduct> product) {
		this.email=email;
		this.fullName=fullName;
		this.product=product;
	}
	
}
