package com.circle_devs.zdanovskih.integration;
import com.circle_devs.zdanovskih.TestTaskForCircleDevsApplication;
import com.circle_devs.zdanovskih.config.AbstractJpaTest;
import com.circle_devs.zdanovskih.dto.AuthorCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.circle_devs.zdanovskih.constant.Const.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Transactional
@SpringBootTest(classes = TestTaskForCircleDevsApplication.class)
@AutoConfigureMockMvc

public class SecurityIT extends AbstractJpaTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void unauthorizedShouldReturn401() throws Exception {
        mockMvc.perform(get(REST_MAP+REST_BOOK_PATH))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void userCanReadButCannotWrite() throws Exception {
        mockMvc.perform(get(REST_MAP+REST_BOOK_PATH)
                        .with( httpBasic("user", "user")))
                .andExpect(status().isOk());

        mockMvc.perform(post(REST_MAP+REST_BOOK_PATH)
                        .with(httpBasic("user", "user")))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanReadAndWrite() throws Exception {
        mockMvc.perform(get(REST_MAP+REST_AUTHOR_PATH)
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk());

        AuthorCreateDto authorCreateDto = new AuthorCreateDto( "Security author");

        mockMvc.perform(post(REST_MAP + REST_AUTHOR_PATH)
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorCreateDto)))
                .andExpect(status().isCreated());
    }

}
