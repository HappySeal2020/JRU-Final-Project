package controller.rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.zdanovskih.Project5Application;
import com.javarush.zdanovskih.controller.rest.PublisherRestController;
import com.javarush.zdanovskih.dto.PublisherDto;
import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.repository.AuthorRepository;
import com.javarush.zdanovskih.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.javarush.zdanovskih.constant.Const.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublisherRestController.class)
@ContextConfiguration(classes = Project5Application.class)
@AutoConfigureMockMvc(addFilters = false)
public class PublisherRestControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherRepository publisherRepository;

    @MockBean
    private AuthorRepository authorRepository;


    @Test
    public void shouldReturnAllPublishers() throws Exception {

        //Create test data
        List<Publisher> publishers = List.of(
                new Publisher(1L, "Питер","https://www.piter.com"),
                new Publisher(2L, "Издательский дом \"Вильямс\"","williamspublishing.com"));

        when(publisherRepository.findAll()).thenReturn(publishers);
        mockMvc.perform(get(REST_MAP+REST_PUBLISHER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Питер"))
                .andExpect(jsonPath("$[1].name").value("Издательский дом \"Вильямс\""));
    }

    @Test
    public void shouldCreateNewPublisher() throws Exception {
        Publisher savedPublisher = new Publisher(1L, "Питер","https://www.piter.com");
        when(publisherRepository.save(any(Publisher.class))).thenReturn(savedPublisher);
        mockMvc.perform(post(REST_MAP+REST_PUBLISHER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "name":"Питер",
                  "site":"https://www.piter.com"
                }
            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Питер"));
    }

    @Test
    public void shouldUpdateExistingPublisher() throws Exception {
        //Publisher newPublisher = new Publisher(1L, "<UNK>","https://www.piter.com");
        Publisher savedPublisher = new Publisher(1L, "Питер","https://www.piter.com");
        when(publisherRepository.save(any(Publisher.class))).thenReturn(savedPublisher);
        mockMvc.perform(put(REST_MAP+REST_PUBLISHER_PATH+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "id":1,
                  "name":"Питер",
                  "site":"https://www.piter.com"
                }
            """))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Питер"));
    }

    @Test
    public void shouldDeleteExistingPublisher() throws Exception {
        Long id = 1L;
        doNothing().when(publisherRepository).deleteById(id);
        mockMvc.perform(delete(REST_MAP+REST_PUBLISHER_PATH+"/"+id))
                .andExpect(status().isNoContent());
        verify(publisherRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldReturnValidationError() throws Exception {
        PublisherDto requestPublisher = new PublisherDto(0L, "", "new site");

        mockMvc.perform(post(REST_MAP+REST_PUBLISHER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPublisher)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name")
                        .value("Publisher name is mandatory field"));
    }

    @Test
    void shouldReturnConflictWhenNameIsNotUnique() throws Exception {

        PublisherDto requestPublisher = new PublisherDto(0L, "New Publisher", "https://www.piter.com");

        when(publisherRepository.save(any()))
                .thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        mockMvc.perform(post(REST_MAP + REST_PUBLISHER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPublisher)))
                .andExpect(status().isConflict())
                .andExpect(content()
                        .string("Data already exists"));
    }


}
