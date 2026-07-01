package com.circle_devs.zdanovskih.service.impl;

import com.circle_devs.zdanovskih.entity.Book;

import java.util.List;

/**
 * Interface for Book service
 */
public interface BookService {
    List<Book> getAllBooks(int page,
                           int size,
                           String name,
                           String author,
                           Integer printYearFrom,
                           Integer printYearTo,
                           String publisher,
                           String bbk,
                           String isbn,
                           Integer pagesFrom,
                           Integer pagesTo);

    Book saveBook(Book book);
    void deleteById(Long id);


}
