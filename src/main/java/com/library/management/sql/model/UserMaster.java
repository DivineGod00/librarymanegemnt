package com.library.management.sql.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.management.wrapper.BaseClassSql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "user_details")
public class UserMaster extends BaseClassSql {

	@Column(name = "name")
	private String name;

	@Column(name = "about")
	private String about;

	@Column(name = "course")
	private String cousre;

	@Column(name = "email")
	private String email;

	@Lob
	@Column(name = "image_data")
	private byte[] imageData;

	@Column(name = "university/college")
	private String university_college;
    
	
	@Column(name = "role")
	private String role;

	private String base64ImageData;

}
