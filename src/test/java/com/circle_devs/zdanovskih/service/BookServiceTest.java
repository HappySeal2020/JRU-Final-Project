package com.circle_devs.zdanovskih.service;

import com.circle_devs.zdanovskih.entity.Author;
import com.circle_devs.zdanovskih.entity.Book;
import com.circle_devs.zdanovskih.entity.Publisher;
import com.circle_devs.zdanovskih.repository.BookRepository;
import com.circle_devs.zdanovskih.specification.BookSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    //Read publishers
    @Test
    public void getBooks_shouldCallRepositoryWithCorrectParameters() {
        int page=0, size=5;
        String name="Java";
        String author = "John Carnell";
        Integer printYearFrom = 1980;
        Integer printYearTo = 2026;
        String publisher = "Manning";
        String bbk ="bbk";
        String isbn = "isbn";
        Integer pagesFrom = 100;
        Integer pagesTo = 1500;
        Pageable expectedPageable= PageRequest.of(page, size, Sort.by("id"));
        BookSpecification.filter(name, author, printYearFrom, printYearTo, publisher, bbk, isbn, pagesFrom, pagesTo);
        Page<Book> mockPage = new PageImpl<>(List.of(new Book()));
        when(bookRepository.findAll(any(Specification.class), eq(expectedPageable)))
                .thenReturn(mockPage);

        List<Book> result = bookService.getAllBooks(page, size, name, author, printYearFrom, printYearTo,
                publisher, bbk, isbn, pagesFrom, pagesTo);

        verify(bookRepository).findAll(any(Specification.class), eq(expectedPageable));
        assertEquals(1, result.size());
    }

    @Test
    public void saveBook(){
        Author author = new Author();
        author.setName("John Carnell");
        Publisher publisher = new Publisher();
        publisher.setName("Manning");
        publisher.setSite("www.manning.com");
        Book book = new Book();
        book.setId(1L);
        book.setName("Alice in wonderland");
        book.setPages(500);
        book.setBbk("bbk");
        book.setIsbn("isbn");
        book.setPrintYear(1980);
        book.setPublisher(publisher);
        book.setAuthors(List.of(author));
        when(bookRepository.saveAndFlush(book)).thenReturn(book);
        Book result = bookService.saveBook(book);
        verify(bookRepository).saveAndFlush(book);
        assertEquals(book.getId(), result.getId());
        assertEquals(book.getName(), result.getName());
        assertEquals(book.getPages(), result.getPages());
        assertEquals(book.getBbk(), result.getBbk());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getPublisher(), result.getPublisher());
        assertEquals(book.getAuthors(), result.getAuthors());
        assertEquals(book.getPrintYear(), result.getPrintYear());
    }

    @Test
    public void deleteBook(){
        Long id = 1L;
        doNothing().when(bookRepository).deleteById(id);
        bookService.deleteById(id);
        verify(bookRepository).deleteById(id);
    }
}
