package com.library.management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.management.sql.model.LoginMaster;

@Repository
public interface LoginRepo extends JpaRepository<LoginMaster, Long> {
	LoginMaster findByUsernameAndPassword(String name, String password);
}
