package com.fido.app.model;

import com.fido.app.entity.VendorProduct;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class CartProducts extends VendorProduct {
	
	private String quantity;

}
