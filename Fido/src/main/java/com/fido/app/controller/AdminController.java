package com.fido.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fido.app.entity.CardDetail;
import com.fido.app.entity.CustomerDetails;
import com.fido.app.entity.Role;
import com.fido.app.entity.VendorDetails;
import com.fido.app.repository.CustomerRepo;
import com.fido.app.repository.VendorRepo;
import com.fido.app.services.Extract_Customer_Vendor;


/**
 * <p>
 *   all the Admin Controls like 
 *   add User/Vendor
 * </p>
 * @author Raushan Kumar
 *
 */
@RestController
@RequestMapping("/api")
public class AdminController {
	
	
//	@Autowired
//	private UserAuthRepo userAuthRepo;
	
	@Autowired 
	private CustomerRepo cusRepo;
	
	@Autowired
	private VendorRepo vendorRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private Extract_Customer_Vendor extCustomer_Vendor;
	
	
//	 user/ customer data get put post below...
	
	@GetMapping(value = "/users")
	public List<CustomerDetails> getAllUserProfile() {
        
		return customerRepo.findAll().stream()
				.filter(customer -> customer.getRoles().stream().allMatch(role -> role.getRole().equals("USER")))
				.map(customer -> {
					return extCustomer_Vendor.extract(customer);
				}).collect(Collectors.toList());

		
	}
	
	 @GetMapping(value = "/userProfile/{id}")
		public CustomerDetails getUserProfileById(@PathVariable("id") long id) {
			return extCustomer_Vendor.extract(customerRepo.findById(id).orElseThrow());

		}
	 
	 @PostMapping("/customer")
	 public String addCustomer(@RequestParam String customer,
		 		@RequestParam(name="aadhar",required = false) MultipartFile file1,
		 		@RequestParam(name="pan",required = false) MultipartFile file2) throws JsonMappingException, JsonProcessingException {
		 
		 System.out.println(customer);
		
		 // change string JSON to customer details...
		 ObjectMapper objectMapper= new ObjectMapper();
		 CustomerDetails custDetails= objectMapper.readValue(customer,CustomerDetails.class);
		 
		 //send files to the CLoudinary api...
		 Map<String,String> config = new HashMap<>();
		 config.put("cloud_name", "dkw5iydb1");
		 config.put("api_key", "123235391421445");
		 config.put("api_secret", "99vLhxjkQ_PmROAlAsaipHXZpUU");
		 Cloudinary cloudinary = new Cloudinary(config);
		 try {
			 @SuppressWarnings("rawtypes")
			Map store=  cloudinary.uploader().upload(file1.getBytes(), ObjectUtils.emptyMap());
			 custDetails.setUrlAadhar((String)store.get("secure_url"));
		    
			store=  cloudinary.uploader().upload(file2.getBytes(), ObjectUtils.emptyMap());
			 custDetails.setUrlPanCard((String)store.get("secure_url"));

		 } catch (IOException exception) {
			  System.out.println(exception.getMessage());
			}

		
		 
		
		
		 Role role =new Role();
		 role.setRole("USER");
		 custDetails.addRoll(role);
		 cusRepo.save(custDetails);
		 
		  return "customer is added";
	 }
		
	
//	 vendor data get put post below...

	@GetMapping(value = "/vendors")
	public List<VendorDetails> getAllVendors() {

		return vendorRepo.findAll().stream()
				.filter(vendor -> vendor.getRoles().stream().allMatch(role -> role.getRole().equals("VENDOR")))
				.map(vendor -> {
					return extCustomer_Vendor.extract(vendor);
				}).collect(Collectors.toList());

	}
	
	 @GetMapping("/vendorProfile/{id}")
		public VendorDetails getVendorProfileById(@PathVariable("id") long id) {
			
			 return extCustomer_Vendor.extract(vendorRepo.findById(id).orElseThrow());
		}
	
	
	 
	 
	 
	
	 
	 @PostMapping("/vendor")
	 public String addVendor(@RequestParam("vendor") String vendor,
			 @RequestParam("aadhar") MultipartFile file1,
			 @RequestParam("pan") MultipartFile file2,
			 @RequestParam(name="doc",required = false) MultipartFile file3) throws JsonMappingException, JsonProcessingException {
		 
		 
		 
		 ObjectMapper objectMapper= new ObjectMapper();
		 VendorDetails vendorDetails= objectMapper.readValue(vendor,VendorDetails.class);
		 
//		 send files to the CLoudinary api...
		 Map<String,String> config = new HashMap<>();
		 config.put("cloud_name", "dkw5iydb1");
		 config.put("api_key", "123235391421445");
		 config.put("api_secret", "99vLhxjkQ_PmROAlAsaipHXZpUU");
		 Cloudinary cloudinary = new Cloudinary(config);
		 try {
			 @SuppressWarnings("rawtypes")
			Map store=  cloudinary.uploader().upload(file1.getBytes(), ObjectUtils.emptyMap());
			
		    vendorDetails.setUrlAadhar((String)store.get("secure_url"));
		    
			store=  cloudinary.uploader().upload(file2.getBytes(), ObjectUtils.emptyMap());
			vendorDetails.setUrlPanCard((String)store.get("secure_url"));
			
			if(file3 !=null) {
			store= cloudinary.uploader().upload(file3.getBytes(), ObjectUtils.emptyMap());
			vendorDetails.setUrlBusinessDoc((String)store.get("secure_url"));
			}
		 } catch (IOException exception) {
			  System.out.println(exception.getMessage());
			}
		 
		 Role role=new Role();
		 role.setRole("VENDOR");
		 vendorDetails.addRoll(role);
		 vendorRepo.save(vendorDetails);
		 return "Vendor is added";
	 }
	 

}
