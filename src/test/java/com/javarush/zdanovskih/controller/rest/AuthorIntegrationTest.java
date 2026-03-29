package com.javarush.zdanovskih.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.zdanovskih.Project5Application;
import com.javarush.zdanovskih.dto.AuthorDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.javarush.zdanovskih.constant.Const.REST_AUTHOR_PATH;
import static com.javarush.zdanovskih.constant.Const.REST_MAP;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc(addFilters = false)
//@ContextConfiguration(classes = Project5Application.class)
@SpringBootTest(classes = Project5Application.class)
public class AuthorIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldFailOnDuplicateName() throws Exception {

        AuthorDto dto = new AuthorDto(0L, "Tolstoy");

        mockMvc.perform(post(REST_MAP + REST_AUTHOR_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post(REST_MAP + REST_AUTHOR_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }
}
