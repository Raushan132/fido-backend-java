package com.fido.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.VendorProduct;
import com.fido.app.repository.VendorProductReop;
import com.fido.app.services.AuthDetail;

/**
 * <p>
 *    Product Controller</br> 
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
public class ProductController {
	
	@Autowired
	private VendorProductReop productReop;
	
		
	@Autowired
	private AuthDetail authDetail;
	
	
	
	@GetMapping(value = "/vendor")
	public String getVendor() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		System.out.println(auth.getPrincipal());// return UserAuth class
//		System.out.println(auth.getDetails());
//		System.out.println(auth.getCredentials());
//		System.out.println(auth.getName()); //return email
//		System.out.println(auth.getAuthorities().stream().allMatch(role-> role.getAuthority().equals("ADMIN")));
		
		return "vendor product is available";
	}
	
	
	
	@PutMapping(value="/setProduct")
	public String setProduct(@RequestBody VendorProduct product) {
		  
		var temp=authDetail.getVendorDetail();
		  product.setProuductOwnerId(temp.getId());
		  productReop.save(product);
		  
		return "Store succussfull";
		
	}

	@GetMapping(value="/getProduct")
	public List<VendorProduct> getProduct() {
		
		List<VendorProduct> products= productReop.findAll();
		
		return products;
	}
	
	@GetMapping("/product/{id}")
	public List<VendorProduct> getProductByVendorId(@PathVariable("id") long id) {
		 return productReop.findAllByProuductOwnerId(id);
	}
	
	@GetMapping("/vendorProducts")
	public List<VendorProduct> getProductByVendor(){
	 
		var temp=authDetail.getVendorDetail();
	    return productReop.findAllByProuductOwnerId(temp.getId());
	}
	
	@GetMapping(value="/getProductDetails/{id}")
	public VendorProduct getProductById(@PathVariable("id") long id) {
		
		  return productReop.findById(id).orElseThrow();
	}
	
	@DeleteMapping(value="/getDeleteProduct/{id}")
	public boolean getDeleteProduct(@PathVariable("id") long id) {
		
						   
		   if(!authDetail.isAdmin()) 
			   return false;
		    	
		    
		  productReop.deleteById(id);
		  return true;
	}
	
	
	
	

}
