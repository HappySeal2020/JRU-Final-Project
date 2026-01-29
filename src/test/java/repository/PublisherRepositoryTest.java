package repository;

import com.javarush.zdanovskih.Project5Application;
import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.repository.AuthorRepository;
import com.javarush.zdanovskih.repository.PublisherRepository;
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
@ContextConfiguration(classes = Project5Application.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PublisherRepositoryTest {
    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindAuthorById() {
        Publisher publisher = new Publisher(0, "Test Publisher","Test site" );
        Publisher savedPublisher = entityManager.persistFlushFind(publisher);
        Optional<Publisher> foundPublisher = publisherRepository.findById(savedPublisher.getId());
        assertTrue(foundPublisher.isPresent());
        assertEquals("Test Publisher", foundPublisher.get().getName());
    }
}
