/**
 * @author
 * Sagar Kumar
 */
package com.sk.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.sk.controllers.AuthController;
import com.sk.entities.User;
import com.sk.security.JwtAuthenticationEntryPoint;
import com.sk.security.JwtAuthenticationFilter;
import com.sk.services.impl.CustomUserDetailService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {


	private Logger logger=LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		
//		
//		UserDetails normal=User.builder()
//		.username("Sagar").password(passwordEncoder().encode("test123")).roles("NORMAL").build();
//		
//		
//
//		UserDetails admin=User.builder().
//		username("Kumar").
//		password(passwordEncoder().encode("test1234")).
//		roles("ADMIN").
//		build();
//		
//		
//		//create users
//		
//		  
//		return new InMemoryUserDetailsManager(normal,admin);
//	}
	
	
//	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
//	{
//	
//		
//		
//			http.authorizeRequests().anyRequest().authenticated().and().	
//			formLogin().loginPage("login.html").loginProcessingUrl("/process")
//			.defaultSuccessUrl("/dashboard").failureUrl("error").and().logout().logoutUrl("/logput");
//			
//		
//			return http.build();
//			
//		
//	}
	
	
	
	
	
	
	
	
	
	
	

	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
	
		
	
	     http.csrf().
	     disable().
         authorizeHttpRequests().
         requestMatchers("/auth/login").
         permitAll(). 
         requestMatchers("/auth/google").
         permitAll(). 
         requestMatchers(HttpMethod.POST,"/users").
         permitAll(). 
         requestMatchers(HttpMethod.DELETE,"/users/**").hasRole("ADMIN").
         requestMatchers(HttpMethod.GET,"/orders/**").permitAll().
         requestMatchers(HttpMethod.POST,"/orders").permitAll()  
         .anyRequest().
         authenticated().
         and().
         exceptionHandling().
         authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement().
         sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	     
        
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
			
		
	}
	
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.customUserDetailService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
		
		
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		
		return new BCryptPasswordEncoder();
		
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
		
		
		 
	}
	
	
	//CORS CONFIGURATION
	
	@SuppressWarnings("unchecked")
	@Bean
	public FilterRegistrationBean corsFilter() {
	
		
		
		logger.info("inside corsFilter");
		CorsConfigurationSource source=new org.springframework.web.cors.UrlBasedCorsConfigurationSource(); 
		
		CorsConfiguration configuration=new CorsConfiguration();
		configuration.setAllowCredentials(true);
		//configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200",));
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("Authorization");
		configuration.addAllowedHeader("Content-Type");
		configuration.addAllowedHeader("Accept");
		
		configuration.addAllowedMethod("GET");
		configuration.addAllowedMethod("POST");
		configuration.addAllowedMethod("DELETE");
		configuration.addAllowedMethod("PUT");
		
		
		((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**", configuration);
		@SuppressWarnings("rawtypes")
		FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean(new CorsFilter(source));
		filterRegistrationBean.setOrder(-100);
		
		return filterRegistrationBean;
	}
	
}
