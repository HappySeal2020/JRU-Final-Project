package controller.rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.zdanovskih.Project5Application;
import com.javarush.zdanovskih.controller.rest.AuthorRestController;
import com.javarush.zdanovskih.dto.AuthorDto;
import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.handler.GlobalExceptionHandler;
import com.javarush.zdanovskih.repository.AuthorRepository;
import com.javarush.zdanovskih.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.javarush.zdanovskih.constant.Const.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthorRestController.class)
@ContextConfiguration(classes = Project5Application.class)
@Import(GlobalExceptionHandler.class)
class AuthorRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PublisherRepository publisherRepository;
    @MockBean
    private AuthorRepository authorRepository;

    @BeforeEach
    void setup() {
        when(authorRepository.save(any())).thenAnswer(i -> i.getArgument(0));
    }

    @Test
     void shouldReturnAllAuthors() throws Exception {
        //Create test data
        List<Author> authors = List.of(new Author(1L, "Джош Лонг"),
                new Author(2L, "Кеннет Бастани"));

        when(authorRepository.findAll()).thenReturn(authors);
        mockMvc.perform(get(REST_MAP+REST_AUTHOR_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Джош Лонг"))
                .andExpect(jsonPath("$[1].name").value("Кеннет Бастани"));
    }

    @Test
    void shouldCreateNewAuthor() throws Exception {
        Author savedAuthor = new Author(1L, "Test Author");
        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);
        mockMvc.perform(post(REST_MAP+REST_AUTHOR_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "name":"Test Author"
                }
            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Author"));
    }

    @Test
    void shouldUpdateExistingAuthor() throws Exception {
        Author savedAuthor = new Author(1L, "New Author");
        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);
        mockMvc.perform(put(REST_MAP+REST_AUTHOR_PATH+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "id":1,
                  "name":"New Author"
                }
            """))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Author"));
    }

    @Test
    void shouldDeleteExistingAuthor() throws Exception {
        Long id = 1L;
        doNothing().when(authorRepository).deleteById(id);
        mockMvc.perform(delete(REST_MAP+REST_AUTHOR_PATH+"/"+id))
                .andExpect(status().isNoContent());
        verify(authorRepository, times(1)).deleteById(id);
    }

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

    @Test
    void shouldReturnConflictWhenNameIsNotUnique() throws Exception {

        AuthorDto requestAuthor = new AuthorDto(0L, "Tolstoy");

        when(authorRepository.save(any()))
                .thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        mockMvc.perform(post(REST_MAP + REST_AUTHOR_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestAuthor)))
                .andExpect(status().isConflict())
                .andExpect(content()
                        .string("Data already exists"));
    }

}
