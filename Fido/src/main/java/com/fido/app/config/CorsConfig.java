package com.fido.app.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  not use if you use spring security...
 *  for practice purpose this class is here...
 *  not for project use
 * @author LENOVO
 *
 */



public class CorsConfig implements WebMvcConfigurer {

	 public void addCorsMappings(CorsRegistry registry) {
		  registry.addMapping("/**")
		  			.allowedOrigins("http://localhost:5173")
		  			.allowedMethods("GET","PUT","DELETE","HEAD","OPTIONS")
		  			.allowCredentials(true);
	 }
}
