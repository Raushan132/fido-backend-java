package com.fido.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;



@Component("auditAwareImpl")
public class AuditorAwareImp implements AuditorAware<String> {
          
	 
	 
		
	@Override
	public Optional<String> getCurrentAuditor() {
		// TODO Auto-generated method stub
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		List<String> list=auth.getAuthorities().stream().map(role->role.getAuthority()).collect(Collectors.toList()); 
	      return Optional.ofNullable(list.get(0));
		
	}
}
