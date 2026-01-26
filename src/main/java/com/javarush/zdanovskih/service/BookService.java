package com.javarush.zdanovskih.service;

import com.javarush.zdanovskih.entity.Book;
import com.javarush.zdanovskih.repository.BookRepository;
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

    public List<Book> getAllBooks() {
        Sort sort = Sort.sort(Book.class).by(Book::getId);
        return bookRepository.findAll(sort);
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
