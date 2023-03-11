package com.fido.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.services.DashboardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class DashboardController {
	
	@Autowired
	private DashboardService dashService;
	
	@GetMapping("/dashboard")
	public Map<String,Object> getDashboard() {
		  Map<String,Object> map= dashService.getAdminDashboardData();
		  log.info(map.toString());
		  return map;
	}
	
    
}
