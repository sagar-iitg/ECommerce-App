/**
 * @author
 * Sagar Kumar
 */
package com.sk.controllers;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.dtos.JwtRequest;
import com.sk.dtos.JwtResponse;
import com.sk.dtos.UserDto;
import com.sk.exception.BadApiRequestException;
import com.sk.security.JwtHelper;
import com.sk.services.UserService;

@RestController
@RequestMapping("/auth")

public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private UserService userService;

	@Autowired
	private JwtHelper jwtHelper;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)
	{
		
		this.doAuthenticate(request.getEmail(),request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String token=this.jwtHelper.generateToken(userDetails);
		
		
		UserDto userDto=mapper.map(userDetails, UserDto.class);
		
		JwtResponse response = JwtResponse.builder().jwtToken(token).user(userDto).build();
		
		return new ResponseEntity<JwtResponse>(response,HttpStatus.OK);
		
	
	}
	
	
	
	
	

	private void doAuthenticate(String email, String password) {
		// TODO Auto-generated method stub
		
		
		UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(email, password);
		
		
		try {
			
			 manager.authenticate(authentication);
			 
		}
		catch(BadCredentialsException e)
		{
				throw new BadApiRequestException("Invalid username or password");
				
		}
	}






	@GetMapping("/current")
	public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
		String name = principal.getName();

		return new ResponseEntity<>(mapper.map(userDetailsService.loadUserByUsername(name), UserDto.class),
				HttpStatus.OK);

	}

}
