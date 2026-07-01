package com.circle_devs.zdanovskih.controller;

import com.circle_devs.zdanovskih.dto.BookCreateDto;
import com.circle_devs.zdanovskih.dto.BookResponseDto;
import com.circle_devs.zdanovskih.dto.BookUpdateDto;
import com.circle_devs.zdanovskih.facade.BookFacadeImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.circle_devs.zdanovskih.constant.Const.REST_BOOK_PATH;
import static com.circle_devs.zdanovskih.constant.Const.REST_MAP;
/**
 * Controller for Books
 * Provides CRUD operations with books.
 */
@Slf4j
@RestController
@RequestMapping(REST_MAP)
@Validated
public class BookController {
    private final BookFacadeImpl bookFacadeImpl;
    public BookController(BookFacadeImpl bookFacadeImpl) {
        this.bookFacadeImpl = bookFacadeImpl;
    }

    //Read
    @Operation(summary = "Чтение всех книг. Фильтр по названию книги, автору, году издания, издателю, bbk, isbn, "
            +"количеству страниц. Пагинация.")
    @GetMapping(REST_BOOK_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponseDto> getAllBooks(@RequestParam(defaultValue = "0") int page,
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
        return bookFacadeImpl.getAllBooks(
                    page,
                    size,
                    name,
                    author,
                    printYearFrom,
                    printYearTo,
                    publisher,
                    bbk,
                    isbn,
                    pagesFrom,
                    pagesTo);
    }

    //Create
    @Operation(summary = "Создание новой книги.")
    @PostMapping (REST_BOOK_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        log.info("Creating book: {}", bookCreateDto);
        return bookFacadeImpl.create(bookCreateDto);
    }

    //Update
    @Operation(summary = "Изменение существующей книги с номером {id}")
    @PutMapping(REST_BOOK_PATH+"/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookUpdateDto updateBook (@PathVariable Long id, @Valid @RequestBody BookUpdateDto bookUpdateDto) {
        log.info("Updating book: {}", bookUpdateDto);
        return bookFacadeImpl.update(id, bookUpdateDto);
    }

    //Delete
    @Operation(summary = "Удаление книги с номером {id}")
    @DeleteMapping(REST_BOOK_PATH+"/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisher(@PathVariable Long id) {
        log.info("REST API - Deleting publisher: {}", id);
        bookFacadeImpl.delete(id);
    }

}
