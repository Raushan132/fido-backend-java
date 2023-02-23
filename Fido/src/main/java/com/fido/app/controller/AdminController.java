package com.fido.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	 @PostMapping("/admin")
	 public String getAdmin(@RequestParam("test") String str,
			 		@RequestParam("file1") MultipartFile file1,
			 		@RequestParam("file2") MultipartFile file2,
			 		@RequestParam(name="file3",required = false ) MultipartFile file3) throws IOException {
		
		 
//		 Map<String,String> config = new HashMap<>();
//		 config.put("cloud_name", "dkw5iydb1");
//		 config.put("api_key", "123235391421445");
//		 config.put("api_secret", "99vLhxjkQ_PmROAlAsaipHXZpUU");
//		 Cloudinary cloudinary = new Cloudinary(config);
//		 try {
//			Map store=  cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//   	    System.out.println(store);
//		    System.out.println(store.get("secure_url"));
//		 } catch (IOException exception) {
//			  System.out.println(exception.getMessage());
//			}
//		 
//		 System.out.println(file.getInputStream());
		
		 
//		 ObjectMapper objectMapper= new ObjectMapper();
//		   UserAuth userAuth= objectMapper.readValue(str, UserAuth.class);
		 
//		 System.out.println(userAuth);
		 
		 return "hello Admin";
		 
	 }
	 
	 
	 
	 @PostMapping("/addCustomer")
	 public String addCustomer(@RequestParam String customer,
		 		@RequestParam(name="aadhar",required = false) MultipartFile file1,
		 		@RequestParam(name="pan",required = false) MultipartFile file2) throws JsonMappingException, JsonProcessingException {
		 
		 System.out.println(customer);
		 System.out.println(file1.getName());
		 System.out.println(file2.getName());
		 // change string JSON to customer details...
		 ObjectMapper objectMapper= new ObjectMapper();
		 CustomerDetails custDetails= objectMapper.readValue(customer,CustomerDetails.class);
		 
		 //send files to the CLoudinary api...
//		 Map<String,String> config = new HashMap<>();
//		 config.put("cloud_name", "dkw5iydb1");
//		 config.put("api_key", "123235391421445");
//		 config.put("api_secret", "99vLhxjkQ_PmROAlAsaipHXZpUU");
//		 Cloudinary cloudinary = new Cloudinary(config);
//		 try {
//			 @SuppressWarnings("rawtypes")
//			Map store=  cloudinary.uploader().upload(file1.getBytes(), ObjectUtils.emptyMap());
//			System.out.println(store);
//		    System.out.println(store.get("secure_url"));
//		    custDetails.setUrlAadhar((String)store.get("secure_url"));
//		    
//			store=  cloudinary.uploader().upload(file2.getBytes(), ObjectUtils.emptyMap());
//			System.out.println(store);
//		    System.out.println(store.get("secure_url"));
//		    custDetails.setUrlPanCard((String)store.get("secure_url"));
//
//		 } catch (IOException exception) {
//			  System.out.println(exception.getMessage());
//			}
//		 
		
		 
		 System.out.println(custDetails);
		 //System.out.println(custDetails.getAddress());
//		 System.out.println(custDetails.getMobile());
		 System.out.println(custDetails.getEmail());
//		 System.out.println(custDetails.getFullName());
//		 System.out.println(custDetails.getUrlAadhar());
//		 System.out.println(custDetails.getUrlPanCard());
		// Role role =new Role();
		 //role.setRole("USER");
		 //custDetails.addRoll(role);
//		 cusRepo.save(custDetails);
		 
		  return "customer is added";
	 }
	 
	 @PostMapping("/addVendor")
	 public String addVendor(@RequestBody VendorDetails vendorDetails) {
		 Role role=new Role();
		 role.setRole("VENDOR");
		 vendorRepo.save(vendorDetails);
		 return "Vendor is added";
	 }

}
