package com.library.management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.management.sql.model.BookCategory;

@Repository
public interface BookCategoryRepo extends JpaRepository<BookCategory, Long> {

}
