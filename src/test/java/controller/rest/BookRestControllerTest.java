package controller.rest;

import com.javarush.zdanovskih.Project5Application;
import com.javarush.zdanovskih.controller.rest.AuthorRestController;
import com.javarush.zdanovskih.controller.rest.BookRestController;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.javarush.zdanovskih.constant.Const.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(BookRestController.class)
@ContextConfiguration(classes = Project5Application.class)
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
        when(bookService.getAllBooks()).thenReturn(books);
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


    /*
    @Test
    void shouldReturnValidationError() throws Exception {
        AuthorDto requestAuthor = new AuthorDto(0L, "");

        mockMvc.perform(post(REST_MAP+REST_AUTHOR_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestAuthor)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name")
                        .value("Author name is mandatory field"));
    }
     */
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
}
