package com.circle_devs.zdanovskih.facade;

import com.circle_devs.zdanovskih.dto.BookCreateDto;
import com.circle_devs.zdanovskih.dto.BookResponseDto;
import com.circle_devs.zdanovskih.dto.BookUpdateDto;
import com.circle_devs.zdanovskih.entity.Author;
import com.circle_devs.zdanovskih.entity.Book;
import com.circle_devs.zdanovskih.entity.Publisher;
import com.circle_devs.zdanovskih.facade.impl.BookFacade;
import com.circle_devs.zdanovskih.mapper.AuthorMapper;
import com.circle_devs.zdanovskih.mapper.PublisherMapper;
import com.circle_devs.zdanovskih.service.AuthorServiceImpl;
import com.circle_devs.zdanovskih.service.BookServiceImpl;
import com.circle_devs.zdanovskih.service.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * facade for Book uses services and returns DTO
 */
@Component
@Slf4j
public class BookFacadeImpl implements BookFacade {
    private final BookServiceImpl bookService;
    private final AuthorServiceImpl authorService;
    private final PublisherServiceImpl publisherService;
    private final PublisherMapper publisherMapper;
    private final AuthorMapper authorMapper;

    public BookFacadeImpl(BookServiceImpl bookService, AuthorServiceImpl authorService, PublisherServiceImpl publisherService, PublisherMapper publisherMapper, AuthorMapper authorMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.publisherMapper = publisherMapper;
        this.authorMapper = authorMapper;
    }

    /**
     * create new book
     * @param dto - BookCreateDto
     * @return BookResponseDto (with id)
     */
    @Override
    public BookResponseDto create(BookCreateDto dto) {
        List<Author> authors =
                authorService.findAllByIds(dto.authorIds());
        Publisher publisher =
                publisherService.findById(dto.publisherId());
        Book book = new Book();
        book.setName(dto.name());
        book.setAuthors(authors);
        book.setPublisher(publisher);
        book.setBbk(dto.bbk());
        book.setIsbn(dto.isbn());
        book.setPages(dto.pages());
        book.setPrintYear(dto.printYear());
        Book saved = bookService.saveBook(book);
        return new
                BookResponseDto(saved.getId(),
                saved.getName(),
                saved.getAuthors()
                     .stream()
                     .map(authorMapper::authorToAuthorResponseDto)
                     .toList(),
                saved.getPrintYear(),
                publisherMapper.publisherToPublisherResponseDto(saved.getPublisher()),
                saved.getBbk(),
                saved.getIsbn(),
                saved.getPages());
    }

    /**
     * get book with filter and pagination
     * @param page - page number (default 0)
     * @param size - page size (default 5)
     * @param name - filter book by name
     * @param author - filter book by author
     * @param printYearFrom - filter book by =printYear (if printYearTo is null)
     * @param printYearTo - filter book by range printYearFrom <= printYear <=printYearTo
     * @param publisher - filter book by publisher
     * @param bbk - filter book by bbk
     * @param isbn - filter book by isbn
     * @param pagesFrom - filter book by =pages (if pagesTo is null)
     * @param pagesTo - filter book by range pagesFrom <= pages <=pagesTo
     * @return List of BookResponseDto
     */
    @Override
    public List <BookResponseDto> getAllBooks(
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
            Integer pagesTo) {

        return bookService.getAllBooks(page, size, name, author, printYearFrom,printYearTo, publisher, bbk, isbn, pagesFrom, pagesTo)
                .stream()
                .map(book -> new BookResponseDto(
                        book.getId(),
                        book.getName(),
                        book.getAuthors()
                                .stream()
                                .map(authorMapper::authorToAuthorResponseDto)
                                .toList(),
                        book.getPrintYear(),
                        publisherMapper.publisherToPublisherResponseDto(book.getPublisher()),
                        book.getBbk(),
                        book.getIsbn(),
                        book.getPages()))
                .collect(Collectors.toList());
    }

    /**
     * update book
     * @param id - book's id
     * @param dto - dto for update
     * @return dto after update
     */
    @Override
    public BookUpdateDto update(Long id, BookUpdateDto dto) {
        List<Author> authors =
                authorService.findAllByIds(dto.authorIds());
        Publisher publisher =
                publisherService.findById(dto.publisherId());
        Book book = new Book();
        book.setId(id);
        book.setName(dto.name());
        book.setAuthors(authors);
        book.setPublisher(publisher);
        book.setBbk(dto.bbk());
        book.setIsbn(dto.isbn());
        book.setPages(dto.pages());
        book.setPrintYear(dto.printYear());
        Book saved = bookService.saveBook(book);
        return new BookUpdateDto(saved.getName(),
                saved.getAuthors()
                        .stream()
                        .map(Author::getId)
                        .toList(),
                saved.getPrintYear(),
                saved.getPublisher().getId(),
                saved.getBbk(),
                saved.getIsbn(),
                saved.getPages());
    }

    //delete

    /**
     * Delete book
     * @param id - book's id
     */
    @Override
    public void delete(Long id) {
        bookService.deleteById(id);
    }

}
