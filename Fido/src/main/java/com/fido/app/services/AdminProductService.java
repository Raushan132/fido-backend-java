package com.fido.app.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fido.app.entity.VendorDetails;
import com.fido.app.entity.VendorProduct;
import com.fido.app.model.AdminProduct;
import com.fido.app.repository.VendorProductReop;
import com.fido.app.repository.VendorRepo;

@Service
public class AdminProductService {

	@Autowired
	private VendorProductReop productReop;
	
	@Autowired
	private VendorRepo vendorRepo;
	
	public List<AdminProduct> getProductWithName() {
		  
		  List<VendorDetails> vendors = vendorRepo.findAll(); 
		  List<AdminProduct> adminProduct=new ArrayList<>();
		  vendors.stream().forEach(vendor->{
			 
			 List<VendorProduct> products= productReop.findAllByProuductOwnerId(vendor.getId());
			 products.stream().forEach(product->{
				 AdminProduct aProduct=new AdminProduct();
				 aProduct.setProduct(product);
				 aProduct.setVName(vendor.getFullName());
				 adminProduct.add(aProduct);
			 });
			 
			 
			 
		  });
		  System.out.println(adminProduct);
		  return adminProduct;
		  
	}
}
