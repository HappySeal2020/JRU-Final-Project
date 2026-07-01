package com.circle_devs.zdanovskih.specification;
import com.circle_devs.zdanovskih.config.AbstractIntegrationTest;
import com.circle_devs.zdanovskih.entity.Book;
import io.restassured.http.ContentType;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.circle_devs.zdanovskih.constant.Const.REST_BOOK_PATH;
import static com.circle_devs.zdanovskih.constant.Const.REST_MAP;
import static io.restassured.RestAssured.given;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Book specification tests via Rest Assured
 * test data from test_books.sql used
 */
public class BookSpecificationTest extends AbstractIntegrationTest {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Sql(scripts={"classpath:/test_books.sql"})

    //  filter by book name
    @Test
    public void shouldFilterBookByName() {
        List<String> names = given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(contextPath+REST_MAP+REST_BOOK_PATH+"?name=Delphi&size=100")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("name");

        assertThat(names.size()).isGreaterThanOrEqualTo(5);
        assertThat(names, everyItem(containsStringIgnoringCase("Delphi")));
    }

    //  filter by author
    @Test
    public void shouldFilterBookByAuthor() {
        List<List<String>> authors = given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(contextPath+REST_MAP+REST_BOOK_PATH+"?author=Хорстманн")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("authors.name");

        assertThat(authors.size()).isGreaterThanOrEqualTo(2);
        assertThat(authors, everyItem(hasItem(containsStringIgnoringCase("Хорстманн"))));
    }

    //  filter by printYear equals
    @Test
    public void shouldFilterBookByEqPrintYear() {
        List<Integer> printYears = given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(contextPath + REST_MAP + REST_BOOK_PATH + "?printYearFrom=2019&size=100")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("printYear");

        assertThat(printYears.size()).isGreaterThanOrEqualTo(6);
        assertThat(printYears, everyItem(equalTo(2019)));
    }

    //  filter by printYear between
    @Test
    public void shouldFilterBookByBetweenPrintYear() {
        List<Integer> printYears = given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(contextPath + REST_MAP + REST_BOOK_PATH + "?printYearFrom=2012&printYearTo=2022&size=100")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("printYear");

        assertThat(printYears.size()).isGreaterThanOrEqualTo(15);
        assertThat(printYears, everyItem(allOf(
                greaterThanOrEqualTo(2012),
                lessThanOrEqualTo(2022))));
    }

    // filter by publisher
    @Test
    public void shouldFilterBookByPublisher() {
        List<String> publishers = given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(contextPath+REST_MAP+REST_BOOK_PATH+"?publisher=Питер&size=100")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("publisher.name");

        assertThat(publishers.size()).isGreaterThanOrEqualTo(5);
        assertThat(publishers, everyItem(containsStringIgnoringCase("Питер")));
    }

    // filter by pages equal
    @Test
    public void shouldFilterBookByEqPages() {
        List<Integer> pages = given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(contextPath + REST_MAP + REST_BOOK_PATH + "?pagesFrom=704")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("pages");

        assertThat(pages.size()).isEqualTo(1);
        assertThat(pages, everyItem(equalTo(704)));
    }

    // filter by pages between
    @Test
    public void shouldFilterBookByBetweenPages() {
        List<Integer> pages = given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(contextPath + REST_MAP + REST_BOOK_PATH + "?pagesFrom=500&pagesTo=700&size=100")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("pages");

        assertThat(pages.size()).isEqualTo(6);
        assertThat(pages, everyItem(allOf(
                greaterThanOrEqualTo(500),
                lessThanOrEqualTo(700))));
    }

    @Test
    void filter_shouldReturnEmptyPredicateWhenNameIsNull(){
        Specification<Book> spec = BookSpecification.filter(null, null, null, null,
                null, null, null, null, null);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        spec.toPredicate(mock(Root.class), mock(CriteriaQuery.class), cb);
        verify(cb).and();
    }


}
