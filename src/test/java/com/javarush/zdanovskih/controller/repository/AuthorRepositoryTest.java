package com.javarush.zdanovskih.controller.repository;

import com.javarush.zdanovskih.Project5Application;
import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.repository.AuthorRepository;
import com.javarush.zdanovskih.specification.AuthorSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.greaterThanOrEqualTo;

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

    @BeforeEach
    void setUp() {
        authorRepository.save(new Author(0, "Pushkin"));
        authorRepository.save(new Author(1, "Faronov"));
        authorRepository.save(new Author(0, "Osipov"));
    }

    @Test
    void shouldFindAuthorById() {
        Author author = new Author(0, "Test Author" );//authorRepository.findById(1L).get();
        Author savedAuthor = entityManager.persistFlushFind(author);
        Optional<Author> foundAuthor = authorRepository.findById(savedAuthor.getId());
        assertTrue(foundAuthor.isPresent());
        assertEquals("Test Author", foundAuthor.get().getName());
    }

    @Test
    void shouldFilterByName() {
        //when
        List<Author> authors = authorRepository.findAll(AuthorSpecification.filter("push"));
        //then
        assertThat(authors).hasSize(1);
        assertThat(authors.get(0).getName()).isEqualTo("Pushkin");
    }

    @Test
    void shouldReturnAllWhenFilterIsNull() {
        //when
        List<Author> authors = authorRepository.findAll(AuthorSpecification.filter(null));
        //then

        assertThat(authors).hasSizeGreaterThanOrEqualTo(3);
    }
}
