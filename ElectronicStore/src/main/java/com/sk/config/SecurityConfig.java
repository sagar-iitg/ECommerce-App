/**
 * @author
 * Sagar Kumar
 */
package com.sk.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sk.security.JwtAuthenticationEntryPoint;
import com.sk.security.JwtAuthenticationFilter;
import com.sk.services.impl.CustomUserDetailService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	
	
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
	
		
	
	     http.csrf()
         .disable()
         .cors()
         .disable().
         authorizeHttpRequests().
         requestMatchers("/auth/login").
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
	
}
