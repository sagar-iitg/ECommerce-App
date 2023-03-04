package com.sk.services;



import java.util.List;


import com.sk.dtos.PageableResponse;
import com.sk.dtos.UserDto;
import com.sk.entities.User;
import java.util.Optional;


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
	
	
	Optional<User> findUserByEmailOptional(String email);
	
	
	//other user specific feature
	
	

}
