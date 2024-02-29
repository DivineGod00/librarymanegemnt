package com.library.management.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.management.sql.model.BooksMaster;

@Repository
public interface BookAddRepo extends JpaRepository<BooksMaster, Long> {

	List<BooksMaster> findByBookName(String bookName);
}
