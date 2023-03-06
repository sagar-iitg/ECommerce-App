package com.sk.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sk.dtos.ApiResponseMessage;
import com.sk.dtos.ImageResponse;
import com.sk.dtos.PageableResponse;
import com.sk.dtos.UserDto;
import com.sk.services.FileService;
import com.sk.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Value("${user.profile.image.path}")
	private String imageUploadPath;

	
	
	private Logger logger= LoggerFactory.getLogger(UserController.class);
	
	// create

	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

		UserDto userDto1 = userService.createUser(userDto);
		return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

	}

	// update

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,
			@Valid @RequestBody UserDto userDto) 
	{

		UserDto updatedUserDto = userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);

	}

	// delete

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) {

		ApiResponseMessage msg = ApiResponseMessage.builder().message("User is deleted successfully").success(true)
				.status(HttpStatus.OK).build();

		userService.deleteUser(userId);
		return new ResponseEntity<>(msg, HttpStatus.OK);

	}

	// get all
	@GetMapping
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

		return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);

	}

	// get single

	@GetMapping("/{userId}")

	public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
		return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
	}

	// get by mail

	@GetMapping("/email/{emailId}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String emailId) {
		return new ResponseEntity<>(userService.getUserByEmail(emailId), HttpStatus.OK);
	}

	// Search User

	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
		return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
	}

	// upload user Image

	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploaduserImage(@RequestParam("userImage") MultipartFile image,
			@PathVariable String userId) throws IOException {

		String imageName = fileService.uploadFile(image, imageUploadPath);

		UserDto user = userService.getUserById(userId);
		user.setImageName(imageName);

		UserDto userDto = userService.updateUser(user, userId);

		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true)
				.status(HttpStatus.CREATED).build();

		return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);

	}

	// serve User Image
	
	

	
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId,HttpServletResponse response) throws IOException
	{
		
		//
		
		UserDto user=userService.getUserById(userId);
		logger.info("user image name {} ",user.getImageName());
		InputStream resource=fileService.getResource(imageUploadPath, user.getImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource,response.getOutputStream());
		
		
		
		
	}
	

}
