package com.library.management.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.management.sql.model.BookIssueReturn;
import com.library.management.sql.model.BooksMaster;

@Repository
public interface BookIssueReturnRepo extends JpaRepository<BookIssueReturn, Long> {

	BookIssueReturn findByIssuedBookIdAndUserId(BooksMaster bookId, Long userId);

	List<BookIssueReturn> findByUserId(Long userId);

}
