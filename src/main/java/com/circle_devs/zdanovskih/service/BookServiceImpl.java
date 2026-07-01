package com.circle_devs.zdanovskih.service;

import com.circle_devs.zdanovskih.entity.Book;
import com.circle_devs.zdanovskih.repository.BookRepository;
import com.circle_devs.zdanovskih.service.impl.BookService;
import com.circle_devs.zdanovskih.specification.BookSpecification;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing Books.
 */
@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks(int page,
                                  int size,
                                  String name,
                                  String author,
                                  Integer printYearFrom,
                                  Integer printYearTo,
                                  String publisher,
                                  String bbk,
                                  String isbn,
                                  Integer pagesFrom,
                                  Integer pagesTo) {

        Sort.sort(Book.class).by(Book::getId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Book> bookPage;
        bookPage = bookRepository.findAll(
                BookSpecification.filter(
                        name,
                        author,
                        printYearFrom,
                        printYearTo,
                        publisher,
                        bbk,
                        isbn,
                        pagesFrom,
                        pagesTo
                ),
                pageable
        );
        return bookPage.getContent();
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
        //bookRepository.flush();
    }

}
