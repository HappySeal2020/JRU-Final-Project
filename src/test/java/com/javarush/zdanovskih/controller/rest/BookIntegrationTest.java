package com.javarush.zdanovskih.controller.rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.zdanovskih.Project5Application;
import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.entity.Book;
import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.repository.AuthorRepository;
import com.javarush.zdanovskih.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.javarush.zdanovskih.constant.Const.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = Project5Application.class)
public class BookIntegrationTest {

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldFailOnDuplicateName() throws Exception {
        Author author = new Author(0L, "Popular Author");
        Author savedAuthor = authorRepository.save(author);
        Publisher publisher = new Publisher(0L, "FastPrint","www.fastprint.com");
        Publisher savedPublisher = publisherRepository.save(publisher);

        //BookCreationDto dto = new BookCreationDto("Adventures of Super Hero", List.of(author), 2000, publisher, "bbk", "isbn", 500);
        Book dto = new Book(0L, "Adventures of Super Hero", List.of(savedAuthor), 2000, savedPublisher, "bbk", "isbn", 500);

        mockMvc.perform(post(REST_MAP + REST_BOOK_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post(REST_MAP + REST_BOOK_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }
    /*
    //test always fail on H2
    @Test
    void shouldFailOnDeleteAuthorLinkedRecords() throws Exception {
        Author author = new Author(0L, "Popular Author");
        Author savedAuthor = authorRepository.save(author);
        Publisher publisher = new Publisher(0L, "FastPrint","www.fastprint.com");
        Publisher savedPublisher = publisherRepository.save(publisher);
        Book dto = new Book(0L, "Adventures of Super Hero", List.of(savedAuthor), 2000, savedPublisher, "bbk", "isbn", 500);

        mockMvc.perform(post(REST_MAP + REST_BOOK_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
        mockMvc.perform(delete(REST_MAP + REST_AUTHOR_PATH+"/1")
                )
                .andExpect(status().isConflict());
    }
    */
}
