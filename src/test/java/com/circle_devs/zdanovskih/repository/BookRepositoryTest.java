package com.circle_devs.zdanovskih.repository;
import com.circle_devs.zdanovskih.TestTaskForCircleDevsApplication;
import com.circle_devs.zdanovskih.config.AbstractJpaTest;
import com.circle_devs.zdanovskih.entity.Author;
import com.circle_devs.zdanovskih.entity.Book;
import com.circle_devs.zdanovskih.entity.Publisher;
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
@ContextConfiguration(classes = TestTaskForCircleDevsApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class BookRepositoryTest extends AbstractJpaTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindAuthorById() {

        Author author = new Author(0L,"Test author");
        Publisher publisher = new Publisher();
        publisher.setName("Test publisher");
        publisher.setSite("Test site");
        entityManager.persist(author);
        entityManager.persist(publisher);
        Book book = new Book();
        book.setName("Test Book");
        book.setAuthors(List.of(author));
        book.setPublisher(publisher);
        book.setPrintYear(2000);
        book.setBbk("bbk");
        book.setIsbn("isbn");
        book.setPages(500);
        Book savedBook = entityManager.persistFlushFind(book);
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getName());
        assertEquals("Test author", foundBook.get().getAuthors().getFirst().getName());
        assertEquals("Test publisher", foundBook.get().getPublisher().getName());
    }

}
