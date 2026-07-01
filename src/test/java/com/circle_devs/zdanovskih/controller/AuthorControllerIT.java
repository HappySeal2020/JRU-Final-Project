package com.circle_devs.zdanovskih.controller;

import com.circle_devs.zdanovskih.config.AbstractIntegrationTest;
import com.circle_devs.zdanovskih.dto.AuthorCreateDto;
import com.circle_devs.zdanovskih.dto.AuthorUpdateDto;
import com.circle_devs.zdanovskih.entity.Author;
import com.circle_devs.zdanovskih.facade.AuthorFacadeImpl;
import com.circle_devs.zdanovskih.repository.AuthorRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.circle_devs.zdanovskih.constant.Const.REST_AUTHOR_PATH;
import static com.circle_devs.zdanovskih.constant.Const.REST_MAP;
import static io.restassured.RestAssured.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Author controller test via Rest Assured
 */

public class AuthorControllerIT extends AbstractIntegrationTest {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorFacadeImpl authorFacade;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Test
    public void shouldCreateAuthor() {
        AuthorCreateDto request = new AuthorCreateDto("Author name RA");
        Long id =
        given()
                .auth()
                .basic("admin", "admin")
                .log().all()
        .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(contextPath+REST_MAP+REST_AUTHOR_PATH)
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getLong("id");

        Author author = authorRepository.findById(id).orElseThrow();
        assertEquals("Author name RA", author.getName());
    }

    @Test
    public void shouldReadAuthor() {
        String checkName="ReadAuthorName";
        AuthorCreateDto  authorCreateDto = new AuthorCreateDto(checkName);
        Long id = authorFacade.create(authorCreateDto).id();

        List<Long> ids = given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(contextPath+REST_MAP+REST_AUTHOR_PATH+"?name="+checkName)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("id",Long.class);

        assertThat(ids).hasSize(1);
        Long getId = ids.getFirst();
        assertEquals(id, getId);
        Author author = authorRepository.findById(id).orElseThrow();
        assertEquals(checkName, author.getName());
    }


    @Test
    public void shouldUpdateAuthor() {
        AuthorCreateDto  authorCreateDto = new AuthorCreateDto("Super Author name RA");
        Long id = authorFacade.create(authorCreateDto).id();
        AuthorUpdateDto request = new AuthorUpdateDto( "Update Super Author name RA");
        given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put(contextPath+REST_MAP+REST_AUTHOR_PATH+"/"+id)
                .then()
                .log().all()
                .statusCode(202)
                .extract()
                .jsonPath();

        Author author = authorRepository.findById(id).orElseThrow();
        assertEquals("Update Super Author name RA", author.getName());

    }

    @Test
    public void shouldDeleteAuthor() {
        AuthorCreateDto  authorCreateDto = new AuthorCreateDto("Delete Author name RA");
        Long id = authorFacade.create(authorCreateDto).id();

                given()
                        .auth()
                        .basic("admin", "admin")
                        .log().all()
                        .contentType(ContentType.JSON)
                        .when()
                        .delete(contextPath + REST_MAP + REST_AUTHOR_PATH+"/"+id)
                        .then()
                        .log().all()
                        .statusCode(204);

        assertFalse(authorRepository.findById(id).isPresent());
    }




    }
