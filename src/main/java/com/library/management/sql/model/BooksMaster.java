package com.library.management.sql.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.management.wrapper.BaseClassSql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
@Table(name = "book_details")
public class BooksMaster extends BaseClassSql {

	@Column(name = "book_name")
	private String bookName;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "book_category_id")
	private BookCategory book_category_id;

	@Lob
	@Column(name = "image_data", nullable = false)
	private byte[] imageData;

	@Column(name = "book_author")
	private String book_author;

	@Column(name = "book_description")
	private String book_description;

	private String base64ImageData;

	@Column(name = "book_id")
	private String bookId;
//	 @Column(name = "issued")
//	 private boolean issued;
}
