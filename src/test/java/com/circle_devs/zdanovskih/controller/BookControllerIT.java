package com.circle_devs.zdanovskih.controller;

import com.circle_devs.zdanovskih.config.AbstractIntegrationTest;
import com.circle_devs.zdanovskih.dto.BookCreateDto;
import com.circle_devs.zdanovskih.dto.BookUpdateDto;
import com.circle_devs.zdanovskih.entity.Author;
import com.circle_devs.zdanovskih.entity.Book;
import com.circle_devs.zdanovskih.facade.BookFacadeImpl;
import com.circle_devs.zdanovskih.repository.AuthorRepository;
import com.circle_devs.zdanovskih.repository.BookRepository;
import com.circle_devs.zdanovskih.repository.PublisherRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.circle_devs.zdanovskih.constant.Const.*;
import static io.restassured.RestAssured.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Book controller test via Rest Assured
 */
public class BookControllerIT extends AbstractIntegrationTest {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    BookFacadeImpl bookFacade;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    @Test
    public void shouldCreateBook() {
        BookCreateDto request = new BookCreateDto("Book name RA",
                List.of(1L),
                2023,
                1L,
                "bbk",
                "isbn",
                600);
        long id;
        id =
                    given()
                            .auth()
                            .basic("admin", "admin")
                            .log().all()
                            .contentType(ContentType.JSON)
                            .body(request)
                            .when()
                            .post(contextPath + REST_MAP + REST_BOOK_PATH)
                            .then()
                            .log().all()
                            .statusCode(201)
                            .extract()
                            .jsonPath()
                            .getLong("id");

        Book book = bookRepository.findById(id).orElseThrow();
        assertEquals("Book name RA", book.getName());
        assertEquals(2023, book.getPrintYear());
    }

    @Test
    public void shouldReadBook() {
        String checkName="ReadBookName";
        BookCreateDto  bookCreateDto = new BookCreateDto(checkName,
                List.of(1L),
                2020,
                1L,
                "bbk",
                "isbn",
                600);
        Long id = bookFacade.create(bookCreateDto).id();
        List<Long> ids = given()
                    .auth()
                    .basic("admin", "admin")
                    .log().all()
                    .contentType(ContentType.JSON)
                    .when()
                    .get(contextPath + REST_MAP + REST_BOOK_PATH + "?name=" + checkName)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract()
                    .jsonPath()
                    .getList("id", Long.class);

        assertThat(ids).hasSize(1);
        Long getId = ids.getFirst();
        assertEquals(id, getId);
        Book book = bookRepository.findById(id).orElseThrow();
        assertEquals(checkName, book.getName());
        assertEquals(2020, book.getPrintYear());
    }


    @Test
    public void shouldUpdateBook() {
        BookCreateDto  bookCreateDto = new BookCreateDto("Super Book name RA",
                List.of(1L),
                2020,
                1L,
                "bbk",
                "isbn",
                600);
        Long id = bookFacade.create(bookCreateDto).id();
        BookUpdateDto request = new BookUpdateDto("Update Super Book name RA",
                    List.of(1L),
                    2021,
                    1L,
                    "bbk",
                    "isbn",
                    600);

        given()
                    .auth()
                    .basic("admin", "admin")
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .when()
                    .put(contextPath + REST_MAP + REST_BOOK_PATH + "/" + id)
                    .then()
                    .log().all()
                    .statusCode(202)
                    .extract()
                    .jsonPath();

        Book book = bookRepository.findById(id).orElseThrow();
        assertEquals("Update Super Book name RA", book.getName());
        assertEquals(2021, book.getPrintYear());
    }

    @Test
    public void shouldDeleteBook() {
        BookCreateDto bookCreateDto = new BookCreateDto("Delete Book name RA",
                List.of(1L),
                2020,
                1L,
                "bbk",
                "isbn",
                600);
        Long id = bookFacade.create(bookCreateDto).id();

        given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(contextPath + REST_MAP + REST_BOOK_PATH + "/" + id)
                .then()
                .log().all()
                .statusCode(204);
        assertFalse(bookRepository.findById(id).isPresent());
    }

    @Test
    void shouldFailOnDeleteAuthorLinkedRecords(){
        BookCreateDto bookCreateDto = new BookCreateDto("Delete Author RA - Fails",
                List.of(1L),
                2020,
                1L,
                "bbk",
                "isbn",
                600);
        Long id = bookFacade.create(bookCreateDto).id();
        given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(contextPath + REST_MAP + REST_AUTHOR_PATH + "/1")
                .then()
                .log().all()
                .statusCode(409);
        assertTrue(authorRepository.findById(1L).isPresent());
    }

    @Test
    void shouldFailOnDeletePublisherLinkedRecords(){
        BookCreateDto bookCreateDto = new BookCreateDto("Delete Publisher RA - Fails",
                List.of(1L),
                2020,
                1L,
                "bbk",
                "isbn",
                600);
        Long id = bookFacade.create(bookCreateDto).id();
        given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(contextPath + REST_MAP + REST_PUBLISHER_PATH + "/1")
                .then()
                .log().all()
                .statusCode(409);
        assertTrue(publisherRepository.findById(1L).isPresent());
    }



}
