package com.fido.app.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UploadFile {
         
	 public String getUploadFile(MultipartFile file) {
		 
		 Map<String,String> config = new HashMap<>();
		 config.put("cloud_name", "dkw5iydb1");
		 config.put("api_key", "123235391421445");
		 config.put("api_secret", "99vLhxjkQ_PmROAlAsaipHXZpUU");
		 Cloudinary cloudinary = new Cloudinary(config);
		 try {
			 @SuppressWarnings("rawtypes")
			Map store=  cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			 System.out.println(store.get("secure_url"));
			return (String)store.get("secure_url");
		    
			
		 } catch (IOException exception) {
			  log.error(exception.getMessage());
			}

		 return "";
	 }
}
