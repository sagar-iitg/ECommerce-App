package com.sk.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto
{

	private String categoryId;
	
	//Validation of title attributes
	@NotBlank(message="title is blank")
	@Size(min=4,message="title must be gretater four charcaters")
	private String title;
	@NotBlank(message="description required")
	private String description;

	private String coverImage;
	
}
