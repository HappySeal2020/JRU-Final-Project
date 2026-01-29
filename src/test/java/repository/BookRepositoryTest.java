package repository;

import com.javarush.zdanovskih.Project5Application;
import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.entity.Book;
import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.repository.AuthorRepository;
import com.javarush.zdanovskih.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//public class BookRepositoryTest {}
@DataJpaTest
@ContextConfiguration(classes = Project5Application.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindAuthorById() {

        Author author = new Author(0L,"Test author");
        Publisher publisher = new Publisher(0L,"Test publisher","Test site");
        entityManager.persist(author);
        entityManager.persist(publisher);
        Book book = new Book(0L, "Test Book", List.of(author), 2000, publisher, "bbk", "isbn", 500);
        Book savedBook = entityManager.persistFlushFind(book);
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getName());
    }
}

