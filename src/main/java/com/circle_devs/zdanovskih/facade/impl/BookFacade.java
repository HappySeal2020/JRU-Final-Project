package com.circle_devs.zdanovskih.facade.impl;

import com.circle_devs.zdanovskih.dto.BookCreateDto;
import com.circle_devs.zdanovskih.dto.BookResponseDto;
import com.circle_devs.zdanovskih.dto.BookUpdateDto;

import java.util.List;

/**
 * Interface for BookFacade
 */
public interface BookFacade {
    BookResponseDto create(BookCreateDto dto);

    List<BookResponseDto> getAllBooks (
            int page,
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

    BookUpdateDto update(Long id, BookUpdateDto dto);

    void delete(Long id);

}
