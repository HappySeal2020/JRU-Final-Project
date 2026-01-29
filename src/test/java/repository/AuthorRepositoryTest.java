package repository;

import com.javarush.zdanovskih.Project5Application;
import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.repository.AuthorRepository;
import com.javarush.zdanovskih.repository.BookRepository;
import com.javarush.zdanovskih.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ContextConfiguration(classes = Project5Application.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindAuthorById() {
        Author author = new Author(0, "Test Author" );//authorRepository.findById(1L).get();
        Author savedAuthor = entityManager.persistFlushFind(author);
        Optional<Author> foundAuthor = authorRepository.findById(savedAuthor.getId());
        assertTrue(foundAuthor.isPresent());
        assertEquals("Test Author", foundAuthor.get().getName());
    }
}
