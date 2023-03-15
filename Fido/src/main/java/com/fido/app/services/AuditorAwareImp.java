package com.fido.app.services;

import java.util.Optional;
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
	      return Optional.ofNullable(auth.getName());
		
	}
}
