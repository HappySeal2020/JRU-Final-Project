package com.javarush.zdanovskih.controller.rest;

import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.entity.Book;
import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.repository.AuthorRepository;
import com.javarush.zdanovskih.repository.BookRepository;
import com.javarush.zdanovskih.repository.PublisherRepository;
import com.javarush.zdanovskih.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.javarush.zdanovskih.constant.Const.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
//@WebMvcTest(BookRestController.class)
//@ContextConfiguration(classes = Project5Application.class)
class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private PublisherRepository publisherRepository;

    @Test
    void shouldReturnAllBooks() throws Exception {
        Author author = new Author(1L,"Test author");
        Publisher publisher = new Publisher(1L,"Test publisher","Test site");
        List<Book> books = List.of(new Book(1L, "Java. Библиотека профессионала, том 2. Расширенные средства программирования, 10-е издание",List.of(author), 2019, publisher, "32.973.26-018.2.75","978-5-9909445-0-3",976),
                new Book(2L, "Создаём нейронную сеть",List.of(author), 2018, publisher, "32.973.26-018.2.75", "978-5-9909445-7-2",272));
        //bookService.getAllBooks(name, printYearFrom,printYearTo, publisher, bbk, isbn, pagesFrom, pagesTo);
        when(bookService.getAllBooks(null,null,null,null,null,null,null, null,null)).thenReturn(books);
        mockMvc.perform(get(REST_MAP+REST_BOOK_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Java. Библиотека профессионала, том 2. Расширенные средства программирования, 10-е издание"))
                .andExpect(jsonPath("$[1].name").value("Создаём нейронную сеть"));
    }

    @Test
    void shouldCreateNewBook() throws Exception {
        Author author = new Author(1L,"Test author");
        Publisher publisher = new Publisher(1L,"Test publisher","Test site");
        Book savedBook = new Book(1L, "Test Book",List.of(author), 2000, publisher, "bbk", "isbn", 500);
        when(bookService.saveBook(any(Book.class))).thenReturn(savedBook);
        mockMvc.perform(post(REST_MAP+REST_BOOK_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "name":"Test Book",
                  "authors": [
                     {
                        "id": 1,
                        "name":"Test Author"
                     }
                  ],
                  "printYear": 2000,
                  "publisher": {
                       "id": 1,
                       "name":"Test Publisher",
                       "site": "Test site"
                     },
                  "bbk":"bbk",
                  "isbn": "isbn",
                  "pages":500
                }
            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Book"));
    }

    @Test
    void shouldUpdateExistingBook() throws Exception {
        Author author = new Author(1L,"Test author");
        Publisher publisher = new Publisher(1L,"Test publisher","Test site");
        Book savedBook = new Book(1L, "Test Book",List.of(author), 2000, publisher, "bbk", "isbn", 500);
        when(bookService.saveBook(any(Book.class))).thenReturn(savedBook);
        mockMvc.perform(put(REST_MAP+REST_BOOK_PATH+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "id": 1,
                  "name":"Test Book",
                  "authors": [
                     {
                        "id": 1,
                        "name":"Test Author"
                     }
                  ],
                  "printYear": 2000,
                  "publisher": {
                       "id": 1,
                       "name":"Test Publisher",
                       "site": "Test site"
                     },
                  "bbk":"bbk",
                  "isbn": "isbn",
                  "pages":500
                }
            """))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Book"));
    }

    @Test
    void shouldDeleteExistingBook() throws Exception {
        Long id = 1L;
        doNothing().when(bookService).deleteById(id);
        mockMvc.perform(delete(REST_MAP+REST_BOOK_PATH+"/"+id))
                .andExpect(status().isNoContent());
        verify(bookService, times(1)).deleteById(id);
    }

    @Test
    void shouldReturnValidationErrorOnSmallPrintYear() throws Exception {
        Author author = new Author(1L,"Test author");
        Publisher publisher = new Publisher(1L,"Test publisher","Test site");
        Book savedBook = new Book(1L, "Test Book",List.of(author), 1000, publisher, "bbk", "isbn", 500);
        when(bookService.saveBook(any(Book.class))).thenReturn(savedBook);
        mockMvc.perform(put(REST_MAP+REST_BOOK_PATH+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "id": 1,
                  "name":"Test Book",
                  "authors": [
                     {
                        "id": 1,
                        "name":"Test Author"
                     }
                  ],
                  "printYear": 1000,
                  "publisher": {
                       "id": 1,
                       "name":"Test Publisher",
                       "site": "Test site"
                     },
                  "bbk":"bbk",
                  "isbn": "isbn",
                  "pages":500
                }
            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.printYear").value("Print year must be greater 1900"));
    }

    @Test
    void shouldReturnValidationErrorOnLargePrintYear() throws Exception {
        Author author = new Author(1L,"Test author");
        Publisher publisher = new Publisher(1L,"Test publisher","Test site");
        Book savedBook = new Book(1L, "Test Book",List.of(author), 3000, publisher, "bbk", "isbn", 500);
        when(bookService.saveBook(any(Book.class))).thenReturn(savedBook);
        mockMvc.perform(put(REST_MAP+REST_BOOK_PATH+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "id": 1,
                  "name":"Test Book",
                  "authors": [
                     {
                        "id": 1,
                        "name":"Test Author"
                     }
                  ],
                  "printYear": 3000,
                  "publisher": {
                       "id": 1,
                       "name":"Test Publisher",
                       "site": "Test site"
                     },
                  "bbk":"bbk",
                  "isbn": "isbn",
                  "pages":500
                }
            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.printYear").value("Print year can not be in future"));
    }

    @Test
    void shouldReturnValidationErrorOnPages() throws Exception {
        Author author = new Author(1L,"Test author");
        Publisher publisher = new Publisher(1L,"Test publisher","Test site");
        Book savedBook = new Book(1L, "Test Book",List.of(author), 3000, publisher, "bbk", "isbn", -1);
        when(bookService.saveBook(any(Book.class))).thenReturn(savedBook);
        mockMvc.perform(put(REST_MAP+REST_BOOK_PATH+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "id": 1,
                  "name":"Test Book",
                  "authors": [
                     {
                        "id": 1,
                        "name":"Test Author"
                     }
                  ],
                  "printYear": 3000,
                  "publisher": {
                       "id": 1,
                       "name":"Test Publisher",
                       "site": "Test site"
                     },
                  "bbk":"bbk",
                  "isbn": "isbn",
                  "pages":-1
                }
            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.pages").value("Number of pages must be positive"));
    }

    @Test
    void shouldFilterBooksByName() throws Exception {
        Author author = new Author(1L,"Famous author");
        Publisher publisher = new Publisher(1L,"Read us print","read-us-print.com");
        List<Book> books = List.of(new Book(1L, "Super Book",List.of(author), 1970, publisher, "bbk", "isbn", 300));

        when(bookService.getAllBooks(eq("Super"), any(), any(),any(), any(), any(), any(), any(), any()))
                .thenReturn(books);
        mockMvc.perform(get(REST_MAP + REST_BOOK_PATH)
                        .param("name", "Super"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Super Book"));
    }

    @Test
    void shouldFilterBooksByAuthor() throws Exception {
        Author author = new Author(1L,"Famous author");
        Publisher publisher = new Publisher(1L,"Read us print","read-us-print.com");
        List<Book> books = List.of(new Book(1L, "Super Book",List.of(author), 1970, publisher, "bbk", "isbn", 300));

        when(bookService.getAllBooks(any(), eq("amous"), any(),any(), any(), any(), any(), any(), any()))
                .thenReturn(books);
        mockMvc.perform(get(REST_MAP + REST_BOOK_PATH)
                        .param("author", "amous"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Super Book"));
    }


    @Test
    void shouldFilterBooksByPrintYear() throws Exception {
        Author author = new Author(1L,"Famous author");
        Publisher publisher = new Publisher(1L,"Read us print","read-us-print.com");
        List<Book> books = List.of(new Book(1L, "Super Book",List.of(author), 1970, publisher, "bbk", "isbn", 300));

        when(bookService.getAllBooks(any(), any(), eq(1970), any(),any(), any(), any(), any(), any() ))
                .thenReturn(books);
        mockMvc.perform(get(REST_MAP + REST_BOOK_PATH)
                        .param("printYearFrom", "1970"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Super Book"));
    }

    @Test
    void shouldFilterBooksByPrintYearBetween() throws Exception {
        Author author = new Author(1L,"Famous author");
        Publisher publisher = new Publisher(1L,"Read us print","read-us-print.com");
        List<Book> books = List.of(new Book(1L, "Super Book",List.of(author), 2000, publisher, "bbk", "isbn", 300),
                new Book(1L, "Super Book. Continue.",List.of(author), 2010, publisher, "bbk", "isbn", 300),
                new Book(1L, "Super Book v2",List.of(author), 2005, publisher, "bbk", "isbn", 300));

        when(bookService.getAllBooks(any(), any(), eq(2000), eq(2010),any(), any(), any(), any(), any() ))
                .thenReturn(books);
        mockMvc.perform(get(REST_MAP + REST_BOOK_PATH)
                        .param("printYearFrom", "2000")
                .param("printYearTo", "2010"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void shouldFilterBooksByPublisher() throws Exception {
        Author author = new Author(1L,"Famous author");
        Publisher publisher = new Publisher(1L,"Read us print","read-us-print.com");
        List<Book> books = List.of(new Book(1L, "Super Book",List.of(author), 1970, publisher, "bbk", "isbn", 300));

        when(bookService.getAllBooks(any(), any(), any(), any(),eq("print"), any(), any(), any(), any() ))
                .thenReturn(books);
        mockMvc.perform(get(REST_MAP + REST_BOOK_PATH)
                        .param("publisher", "print"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Super Book"));
    }

    @Test
    void shouldFilterBooksByBbk() throws Exception {
        Author author = new Author(1L,"Famous author");
        Publisher publisher = new Publisher(1L,"Read us print","read-us-print.com");
        List<Book> books = List.of(new Book(1L, "Super Book",List.of(author), 1970, publisher, "2343167", "isbn", 300));

        when(bookService.getAllBooks(any(), any(), any(), any(), any(),eq("3167"), any(), any(), any() ))
                .thenReturn(books);
        mockMvc.perform(get(REST_MAP + REST_BOOK_PATH)
                        .param("bbk", "3167"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Super Book"));
    }

    @Test
    void shouldFilterBooksByIsbn() throws Exception {
        Author author = new Author(1L,"Famous author");
        Publisher publisher = new Publisher(1L,"Read us print","read-us-print.com");
        List<Book> books = List.of(new Book(1L, "Super Book",List.of(author), 1970, publisher, "bbk", "8-800-200-8002", 300));

        when(bookService.getAllBooks(any(), any(), any(), any(), any(), any(), eq("8002"),any(), any() ))
                .thenReturn(books);
        mockMvc.perform(get(REST_MAP + REST_BOOK_PATH)
                        .param("isbn", "8002"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Super Book"));
    }

    @Test
    void shouldFilterBooksByPages() throws Exception {
        Author author = new Author(1L,"Famous author");
        Publisher publisher = new Publisher(1L,"Read us print","read-us-print.com");
        List<Book> books = List.of(new Book(1L, "Super Book",List.of(author), 1970, publisher, "bbk", "isbn", 314));

        when(bookService.getAllBooks(any(), any(), any(), any(), any(), any(), any(), eq(314), any() ))
                .thenReturn(books);
        mockMvc.perform(get(REST_MAP + REST_BOOK_PATH)
                        .param("pagesFrom", "314"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Super Book"));
    }

    @Test
    void shouldFilterBooksByPagesBetween() throws Exception {
        Author author = new Author(1L,"Famous author");
        Publisher publisher = new Publisher(1L,"Read us print","read-us-print.com");
        List<Book> books = List.of(new Book(1L, "Super Book",List.of(author), 2000, publisher, "bbk", "isbn", 300),
                new Book(1L, "Super Book. Continue.",List.of(author), 2010, publisher, "bbk", "isbn", 700),
                new Book(1L, "Super Book v2",List.of(author), 2005, publisher, "bbk", "isbn", 500));

        when(bookService.getAllBooks(any(), any(), any(), any(), any(), any(), any(), eq(300) , eq(700)))
                .thenReturn(books);
        mockMvc.perform(get(REST_MAP + REST_BOOK_PATH)
                        .param("pagesFrom", "300")
                        .param("pagesTo", "700"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }
}
