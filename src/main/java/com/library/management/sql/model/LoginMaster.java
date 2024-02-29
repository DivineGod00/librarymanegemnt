package com.library.management.sql.model;

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
@Table(name = "login_credentials")
public class LoginMaster extends BaseClassSql {

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	private UserMaster userId;

}
