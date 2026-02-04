package com.javarush.zdanovskih.service;

import com.javarush.zdanovskih.entity.Book;
import com.javarush.zdanovskih.repository.BookRepository;
import com.javarush.zdanovskih.specification.BookSpecification;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks(String name,
                                  String author,
                                  Integer printYearfrom,
                                  Integer printYearto,
                                  String publisher,
                                  String bbk,
                                  String isbn,
                                  Integer pagesFrom,
                                  Integer pagesTo) {
        Sort sort = Sort.sort(Book.class).by(Book::getId);
        return bookRepository.findAll(BookSpecification.filter(name, author, printYearfrom, printYearto, publisher, bbk, isbn, pagesFrom, pagesTo),sort);
    }

    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

}
