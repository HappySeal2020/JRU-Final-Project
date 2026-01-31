package com.javarush.zdanovskih.controller.http;


import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.entity.Book;
import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.repository.AuthorRepository;
import com.javarush.zdanovskih.repository.BookRepository;
import com.javarush.zdanovskih.repository.PublisherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller

public class BooksController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    public BooksController(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @GetMapping("/books")
    public String books(Model model, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookRepository.findAll(pageable);
        model.addAttribute("booksPage", booksPage);
        model.addAttribute("currentPage", page);
        return "books";
    }


    @GetMapping("/books/put/{id}")
    public String putBook(@PathVariable long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow();
        List<Author> allAuthors = authorRepository.findAll();
        List<Publisher> allPublishers = publisherRepository.findAll();
        model.addAttribute("book", book);
        model.addAttribute("allAuthors", allAuthors);
        model.addAttribute("allPublishers", allPublishers);
        log.info("Edit book: input id={}, found in db: id={} name={}",id, book.getId(), book.getName() );
        return "putBook";
    }

    @PutMapping("/books/put")
    public String updBook(@ModelAttribute Book book) {
        log.info("Try to put book id={} name={}...", book.getId(), book.getName());
        bookRepository.save(book);
        return "redirect:/books";
    }
    @GetMapping("/books/del/{id}")
    public String delBook(@PathVariable long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow();
        model.addAttribute("book", book);
        log.info("Del book: input id={}, found in db: id={} name={}",id, book.getId(), book.getName() );
        return "delBook";
    }

    @DeleteMapping("/books/del")
    public String removeBook(@ModelAttribute Book book) {
        log.info("Try to delete book id={} name={}...", book.getId(), book.getName());
        bookRepository.deleteById(book.getId());
        return "redirect:/books";
    }

    @GetMapping("/books/add")
    public String addBook( Model model) {
        List<Author> allAuthors = authorRepository.findAll();
        List<Publisher> allPublishers = publisherRepository.findAll();
        model.addAttribute("book", new Book(1L, "", null, 0, null, "", "", 0));
        model.addAttribute("allAuthors", allAuthors);
        model.addAttribute("allPublishers", allPublishers);
        log.info("Add book" );
        return "addBook";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book) {
        log.info("Try to add book id={} name={}...", book.getId(), book.getName());
        book.setId(0);
        bookRepository.save(book);
        return "redirect:/books";
    }



}
