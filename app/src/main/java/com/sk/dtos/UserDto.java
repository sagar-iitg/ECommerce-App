package com.sk.dtos;


import java.util.HashSet;
import java.util.Set;

import com.sk.validate.ImageNameValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder	

public class UserDto {
	
	
	

	private String userId;
	
	@Size(min=3,max=50,message="Invalid name")
	private String name;
	
	
	
//	@Email(message="Invalid user mail")
	@Pattern(regexp="^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message="Invalid User regex Email")
	@NotBlank(message="Email is required!!")
	private String email;
	 	


	



	@NotBlank(message="password is required")
	private String password;
	
	private String gender;
	

	@NotBlank(message="Write something about yourself")
	private String about;
	
	
	//@Pattern @Custom Validator
	
	@ImageNameValid()
	private String imageName;
	private Set<RoleDto> roles=new HashSet<>();
	


}
