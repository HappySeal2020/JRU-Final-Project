package com.javarush.zdanovskih.controller.http;


import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.entity.Book;
import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.repository.AuthorRepository;
import com.javarush.zdanovskih.repository.BookRepository;
import com.javarush.zdanovskih.repository.PublisherRepository;
import com.javarush.zdanovskih.specification.BookSpecification;
import com.javarush.zdanovskih.specification.PublisherSpecification;
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
                             @RequestParam(defaultValue = "5") int size,
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false) String author,
                        @RequestParam(required = false) Integer printYearFrom,
                        @RequestParam(required = false) Integer printYearTo,
                        @RequestParam(required = false) String publisher,
                        @RequestParam(required = false) String bbk,
                        @RequestParam(required = false) String isbn,
                        @RequestParam(required = false) Integer pagesFrom,
                        @RequestParam(required = false) Integer pagesTo) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage;
        log.info("Getting books: page={}, size={}, name={}, author={}, printYearFrom={}, printYearTo={}, publisher={}, bbk={}, isbn={}, pagesFrom={}, pagesTo={}",
                page, size, name, author, printYearFrom, printYearTo, publisher, bbk, isbn, pagesFrom, pagesTo);
        if((name!=null && !name.isBlank()) ||
                (author!=null && !author.isBlank()) ||
                (printYearFrom!=null) ||
                (printYearTo!=null) ||
                (publisher!=null && !publisher.isBlank()) ||
                (bbk!=null && !bbk.isBlank()) ||
                (isbn!=null && !isbn.isBlank()) ||
                (pagesFrom !=null) ||
                (pagesTo !=null)) {
            booksPage = bookRepository.findAll(BookSpecification.filter(name, author, printYearFrom, printYearTo,
                    publisher, bbk, isbn, pagesFrom, pagesTo),pageable);
        }
        else {
            booksPage = bookRepository.findAll(pageable);
        }
        model.addAttribute("booksPage", booksPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);
        model.addAttribute("author", author);
        model.addAttribute("printYearFrom", printYearFrom);
        model.addAttribute("printYearTo", printYearTo);
        model.addAttribute("publisher", publisher);
        model.addAttribute("bbk", bbk);
        model.addAttribute("isbn", isbn);
        model.addAttribute("pagesFrom", pagesFrom);
        model.addAttribute("pagesTo", pagesTo);

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
