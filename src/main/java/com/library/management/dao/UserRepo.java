package com.library.management.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.management.sql.model.UserMaster;

@Repository
public interface UserRepo extends JpaRepository<UserMaster, Long> {

//	List<UserMaster> findById(UserMaster id);
	
	

}
