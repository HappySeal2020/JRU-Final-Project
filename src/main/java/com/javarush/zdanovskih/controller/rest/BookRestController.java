package com.javarush.zdanovskih.controller.rest;

import com.javarush.zdanovskih.entity.Book;
import com.javarush.zdanovskih.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.InputMismatchException;
import java.util.List;

import static com.javarush.zdanovskih.constant.Const.REST_BOOK_PATH;
import static com.javarush.zdanovskih.constant.Const.REST_MAP;

@Slf4j
@RestController
@RequestMapping(REST_MAP)
@Validated
public class BookRestController {
    private final BookService bookService;
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }



    //Read
    @GetMapping(REST_BOOK_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String author,
                                  @RequestParam(required = false) Integer printYearFrom,
                                  @RequestParam(required = false) Integer printYearTo,
                                  @RequestParam(required = false) String publisher,
                                  @RequestParam(required = false) String bbk,
                                  @RequestParam(required = false) String isbn,
                                  @RequestParam(required = false) Integer pagesFrom,
                                  @RequestParam(required = false) Integer pagesTo) {
        return bookService.getAllBooks(name, author, printYearFrom,printYearTo, publisher, bbk, isbn, pagesFrom, pagesTo);
    }
    //Create
    @PostMapping (REST_BOOK_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody Book book) {
        book.setId(0);
        log.info("REST API - Creating book: {}", book);
        return bookService.saveBook(book);
    }

    //Update
    @PutMapping(REST_BOOK_PATH+"/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Book updateBook (@PathVariable Long id, @Valid @RequestBody Book book) {
        if(id.equals(book.getId())) {
            log.info("REST API - Updating book: {}", book);
            return bookService.saveBook(book);
        }
        else {
            log.error("REST API - Update author with incorrect id={}", id);
            throw badRequest(new InputMismatchException("Incorrect id"));
        }
    }

    //Delete
    @DeleteMapping(REST_BOOK_PATH+"/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisher(@PathVariable Long id) {
        log.info("REST API - Deleting publisher: {}", id);
        bookService.deleteById(id);
    }

    private ResponseStatusException badRequest(Exception e) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }

}
