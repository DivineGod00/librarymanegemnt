package com.library.management.sql.model;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.management.wrapper.BaseClassSql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@DynamicInsert
@Table(name = "book_category")
public class BookCategory extends BaseClassSql {

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "genre")
	private String genre;

}
