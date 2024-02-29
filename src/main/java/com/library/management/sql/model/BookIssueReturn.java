package com.library.management.sql.model;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.management.wrapper.BaseClassSql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "book_issue_return")
public class BookIssueReturn extends BaseClassSql {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "issued_book_id")
	private BooksMaster issuedBookId;

//	@Column(name = "issued_book_id")
//	private Long[] issueBookId;

	@Column(name = "issued_date")
	private LocalDate issued_date;

	@Column(name = "return_date")
	private LocalDate returnDate;

	@Column(name = "user_id")
	private long userId;

}
