package com.luv2code.springbootlibrary.dao;

import com.luv2code.springbootlibrary.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByBookId(@RequestParam("book_id") Long bookId, Pageable pageable);

    /**
     * Example usage:
     * http://localhost:8080/api/reviews/search/findByBookId?bookId=2
     */

    Review findByUserEmailAndBookId(String userEmail, Long bookId);

    /**
     * Purpose: The @Modifying annotation is used to signal that a query method, annotated with @Query, will modify the state of the database.
     * This is important because Spring Data JPA needs to ensure proper transaction management for such methods.
     *
     * Transaction Management: When you mark a method with @Modifying, Spring Data JPA understands that the method performs a write operation and needs to be executed within a transaction.
     * Spring will automatically start and commit/rollback a transaction for the annotated method.
     *
     * Important Notes:
     * Ensure that the method annotated with @Modifying returns void or int.
     * If it returns void, the operation is executed but the result is not used.
     * If it returns int, the number of affected rows is returned.
     * Make sure the @Modifying annotated method is executed within a transaction, either by having it called from another transactional method or by annotating its containing class with @Transactional
     *
     *
     * Summary:
     * The @Modifying annotation is used to enhance the @Query annotation so that we can execute not only SELECT queries,
     * but also INSERT, UPDATE, DELETE, and even DDL queries.
     */


    @Modifying
    @Query("delete from Review where bookId in :book_id")
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
