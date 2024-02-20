package com.luv2code.springbootlibrary.dao;

import com.luv2code.springbootlibrary.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);
    /**
     * findbookByTitleContaining ====> Error
     * findBookByTitleContaining ====> No Error
     * findBooksByTitleContaining ====> No Error
     * findByTitleContaining ====> No Error
     */

    /**
     Page<Book>: This indicates that the method returns a Page of Book objects.
      In the context of Spring Data JPA, a Page is often used to represent a paginated list of results.

     findByTitleContaining: This is the name of the method.
      It suggests that the method is used to find entities (in this case, books) based on a title that contains a certain substring.
      The Containing keyword implies a partial match.

     @RequestParam("title") String title: This part indicates that the method expects a request parameter named "title" to be passed when calling the method.
     The value of this parameter is of type String and is assigned to the local variable title.
     This is often used to extract data from the request URL.

     Pageable pageable: This parameter is of type Pageable, which is a Spring Data interface used for pagination.
     It allows you to specify the page number, page size, and sorting options for the result set.
     This parameter is typically used to control the pagination of the query results.

     Example usage: http://localhost:8080/api/books/search/findByTitleContaining?title=guru&page=0&size=5
     */

    Page<Book> findByCategory(@RequestParam("category") String category, Pageable pageable);

    // we're saying here is override our find books by book IDs because spring boot will not be able
    // to understand what we need from this.
    @Query("select o from Book o where id in :book_ids")
    List<Book> findBooksByBookIds (@Param("book_ids") List<Long> bookId);
}
