package com.circle_devs.zdanovskih.repository;
import com.circle_devs.zdanovskih.TestTaskForCircleDevsApplication;
import com.circle_devs.zdanovskih.config.AbstractJpaTest;
import com.circle_devs.zdanovskih.entity.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//public class PublisherRepositoryTest {}
@DataJpaTest
@ContextConfiguration(classes = TestTaskForCircleDevsApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class PublisherRepositoryTest extends AbstractJpaTest {
    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindAuthorById() {
        Publisher publisher = new Publisher();
        publisher.setName("Test Publisher");
        publisher.setSite("Test Site");
        Publisher savedPublisher = entityManager.persistFlushFind(publisher);
        Optional<Publisher> foundPublisher = publisherRepository.findById(savedPublisher.getId());
        assertTrue(foundPublisher.isPresent());
        assertEquals("Test Publisher", foundPublisher.get().getName());
        assertEquals("Test Site", foundPublisher.get().getSite());
    }


}
