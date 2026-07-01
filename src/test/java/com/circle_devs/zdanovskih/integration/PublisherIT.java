package com.circle_devs.zdanovskih.integration;
import com.circle_devs.zdanovskih.TestTaskForCircleDevsApplication;
import com.circle_devs.zdanovskih.config.AbstractJpaTest;
import com.circle_devs.zdanovskih.dto.PublisherCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.circle_devs.zdanovskih.constant.Const.REST_MAP;
import static com.circle_devs.zdanovskih.constant.Const.REST_PUBLISHER_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("dev")
@Transactional
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = TestTaskForCircleDevsApplication.class)

public class PublisherIT extends AbstractJpaTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldFailOnDuplicateName() throws Exception {

        PublisherCreateDto dto = new PublisherCreateDto( "FastPrint","www.fastprint.com");

        mockMvc.perform(post(REST_MAP + REST_PUBLISHER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post(REST_MAP + REST_PUBLISHER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

}
