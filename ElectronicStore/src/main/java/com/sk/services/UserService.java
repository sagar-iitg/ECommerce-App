package com.sk.services;

import java.io.IOException;
import java.util.List;

import com.sk.dtos.PageableResponse;
import com.sk.dtos.UserDto;

public interface UserService {
	
	
	
	//Create User
	
	UserDto createUser(UserDto userDto);
	
	//Update User 
	UserDto updateUser(UserDto userDto,String userId);	
	
	//delete User
	
	void deleteUser(String userId);
	
	//get all  users
	
	PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy,String sortDir);	
	
	
	//get single user by id
	
	UserDto getUserById(String userId);
	
	
	//get user my email id
	
	UserDto getUserByEmail(String email);
	
	//search user by name
	
	List<UserDto> searchUser(String keyword);
	
	
	
	
	//other user specific feature
	
	

}
