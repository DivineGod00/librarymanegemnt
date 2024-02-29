package com.library.management.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.management.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

	private Long id;
	private String rollno;
	private String mail; // usermaster
	private String password;
	private MultipartFile imageData; // usermaster
	private String name; // usermaster
	private String about; // usermaster
	private String course; // usermaster
	private String university_college; // usermaster
	private String role; // usermaster
}
