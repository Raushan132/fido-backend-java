package com.fido.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fido.app.entity.VendorProduct;

@Service
public class ExtractCart {
	
	public List<Long> extractIdsFormProudcts(List<VendorProduct> vendorProducts) {
		   
		return vendorProducts.stream().map(product->product.getId()).collect(Collectors.toList());
		
	}

}
