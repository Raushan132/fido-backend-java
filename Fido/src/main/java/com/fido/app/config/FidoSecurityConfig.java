package com.fido.app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import com.fido.app.services.CustomUserDetailService;


@Configuration
@EnableWebSecurity
public class FidoSecurityConfig {

	
	
	@Autowired
	private JwtAuthenticationFilter jwtFilter;
	
    @Autowired
    private CustomUserDetailService customUserDetailService;

	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		
		 CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
	        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:5500","http://127.0.0.1:5500"));
	        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH"));
	        corsConfiguration.setAllowCredentials(true);
	        corsConfiguration.setExposedHeaders(List.of("Authorization"));
		
		
		http.authorizeHttpRequests((authz) -> {
			try {
				authz.antMatchers("/test").permitAll().antMatchers("/token").permitAll()
				.antMatchers("/getProduct").permitAll()
				.antMatchers("/setProduct").permitAll()
				.antMatchers("/getProductDetails/{id}").permitAll()
				.antMatchers("/getDeleteProduct/{id}").authenticated()
//				.antMatchers("/userProfile/{id}").permitAll()
						.anyRequest().permitAll()
						 .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		).httpBasic();
		
		http.authenticationProvider(daoAuthenticationProvider());
		http.csrf().disable();
		http.cors().configurationSource(request->corsConfiguration);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
//		configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
	
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		var provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailService);
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return provider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}  
	  
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
