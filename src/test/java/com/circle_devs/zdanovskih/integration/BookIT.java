package com.circle_devs.zdanovskih.integration;
import com.circle_devs.zdanovskih.TestTaskForCircleDevsApplication;
import com.circle_devs.zdanovskih.config.AbstractJpaTest;
import com.circle_devs.zdanovskih.dto.BookCreateDto;
import com.circle_devs.zdanovskih.entity.Author;
import com.circle_devs.zdanovskih.entity.Publisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.circle_devs.zdanovskih.repository.PublisherRepository;
import com.circle_devs.zdanovskih.repository.AuthorRepository;

import java.util.List;

import static com.circle_devs.zdanovskih.constant.Const.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = TestTaskForCircleDevsApplication.class)

public class BookIT extends AbstractJpaTest {
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
        Author author = new Author();
        author.setName("Popular Author");
        Author savedAuthor = authorRepository.save(author);
        Publisher publisher = new Publisher();
        publisher.setName("FastPrint");
        publisher.setSite("www.fastprint.com");
        Publisher savedPublisher = publisherRepository.save(publisher);

        BookCreateDto bookDto = new BookCreateDto(
                "Adventures of Super Hero",
                List.of(savedAuthor.getId()),
                2000,
                savedPublisher.getId(),
                "bbk",
                "isbn",
                500);

        mockMvc.perform(post(REST_MAP + REST_BOOK_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post(REST_MAP + REST_BOOK_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isConflict());
    }



}
