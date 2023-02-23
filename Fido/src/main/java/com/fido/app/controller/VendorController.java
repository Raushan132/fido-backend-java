package com.fido.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.VendorProduct;
import com.fido.app.repository.VendorProductReop;

/**
 * <p>
 *    VendorController</br> 
 *      add the product by vendor and ADMIN</br>
 *      get all the product by vendor and ADMIN</br>
 *      get  specific product detail by id and email</br>
 *      delete the product by id 
 *  </p>
 * 
 * @author LENOVO
 *
 */

@RestController
public class VendorController {
	
	@Autowired
	private VendorProductReop productReop;
	
	
	
	@GetMapping(value = "/vendor")
	public String getVendor() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getPrincipal());// return UserAuth class
		System.out.println(auth.getDetails());
		System.out.println(auth.getCredentials());
		System.out.println(auth.getName()); //return email
		System.out.println(auth.getAuthorities().stream().allMatch(role-> role.getAuthority().equals("ADMIN")));
		
		return "vendor product is available";
	}
	
	
	
	@PutMapping(value="/setProduct")
	public String setProduct(@RequestBody VendorProduct product) {
		  
		  product.setProuductOwnerId(1);
		  productReop.save(product);
		  
		return "Store succussfull";
		
	}

	@GetMapping(value="/getProduct")
	public List<VendorProduct> getProduct() {
		
		List<VendorProduct> products= productReop.findAll();
		
		return products;
	}
	
	@GetMapping(value="/getProductDetails/{id}")
	public VendorProduct getProductById(@PathVariable("id") long id) {
		
		  return productReop.findById(id).orElseThrow();
	}
	
	@DeleteMapping(value="/getDeleteProduct/{id}")
	public boolean getDeleteProduct(@PathVariable("id") long id) {
		
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			   
		   if(!auth.getAuthorities().stream().allMatch(role-> role.getAuthority().equals("ADMIN"))) 
		   return false;
		    	
		    
		  productReop.deleteById(id);
		  return true;
	}
	
	
	
	

}
