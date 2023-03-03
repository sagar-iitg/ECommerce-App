package com.sk.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.sk.dtos.PageableResponse;
import com.sk.dtos.UserDto;
import com.sk.entities.Role;
import com.sk.entities.User;
import com.sk.exception.ResourceNotFoundException;
import com.sk.helper.Helper;
import com.sk.repositories.RoleRepository;
import com.sk.repositories.UserRepository;
import com.sk.services.UserService;






@Service
public class UserServiceImpl implements UserService{

	
	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	
	@Value("${user.profile.image.path}")
	private String imagePath;
	
	private Logger logger=LoggerFactory.getLogger(UserService.class);
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${normal.role.id}")
	private String normalRoleId;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDto createUser(UserDto userDto) {
 		
		String userId=UUID.randomUUID().toString();
		
		userDto.setUserId(userId);
		
		
		//set encoding password
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		//dto ->entity
		
		User user=dtoToEntity(userDto);
		
		
		
		//fetch of normal user and set role
		
		Role role = roleRepository.findById(normalRoleId).get();
		user.getRoles().add(role);
		
		
		
		
		User savedUser=userRepository.save(user);
		
		
		//
		UserDto newDto=entityToDto(savedUser);
		return newDto;
		
		
	}


	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		// TODO Auto-generated method stub
		
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with given id"));
		
		
	
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setGender(userDto.getGender());
		user.setImageName(userDto.getImageName());
		user.setPassword(userDto.getPassword());
		
		
		 User updatedUser=userRepository.save(user);
		 UserDto updatedDto=entityToDto(updatedUser);
		 
		
	
		return updatedDto;
	}
	
	
	

	@Override
	public void deleteUser(String userId){
		// TODO Auto-generated method stub
		

		User userById=userRepository.
				findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
		
		
		
		//delete user profile image
		//image/user/abc.png
		
		String fullPath=imagePath+userById.getImageName();
		try {
			Path path=Paths.get(fullPath);
			Files.delete(path);
		}catch(NoSuchFileException ex) {
			logger.info("User image not found in folder");
			ex.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		//delete
		userRepository.delete(userById);
		
	}

	@Override
	public PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

		
		
		//		
//		Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
//		Page<User> page=userRepository.findAll(pageable);
//		List<User> users=page.getContent();
//		
//		//convert List of User to List of Dto
//		List<UserDto> dtoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
//		
//		return dtoList;
		
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<User> page=userRepository.findAll(pageable);
		PageableResponse<UserDto> response=Helper.getPageableResponse(page, UserDto.class);
		
		return response;
		
		
		
		
	}

	@Override
	public UserDto getUserById(String userId) {
		// TODO Auto-generated method stub
		
		
		
		User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found with given id"));
		
		
		return entityToDto(user);
		
	}

	@Override
	public UserDto getUserByEmail(String email) {
		// TODO Auto-generated method stub
		
		User user=userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user not found with given email id"));
		
		
		
		
		
		return entityToDto(user);
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		// TODO Auto-generated method stub
		
		List<User> users=userRepository.findByNameContaining(keyword);
		//convert List of User to List of Dto
		List<UserDto> dtoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
			
				return dtoList;
		
	
	}
	
	
	
	private UserDto entityToDto(User savedUser) {
		
		
		return mapper.map(savedUser,UserDto.class);
		
		
	}
	
	
	private User dtoToEntity(UserDto userDto)
	{
		
		return mapper.map(userDto,User.class);
		
	}
	
	
}


	
	
	
		

