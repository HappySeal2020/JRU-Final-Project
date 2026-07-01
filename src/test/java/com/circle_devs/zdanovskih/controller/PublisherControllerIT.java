package com.circle_devs.zdanovskih.controller;
import com.circle_devs.zdanovskih.config.AbstractIntegrationTest;
import com.circle_devs.zdanovskih.dto.PublisherCreateDto;
import com.circle_devs.zdanovskih.dto.PublisherUpdateDto;
import com.circle_devs.zdanovskih.entity.Publisher;
import com.circle_devs.zdanovskih.facade.PublisherFacadeImpl;
import com.circle_devs.zdanovskih.repository.PublisherRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.circle_devs.zdanovskih.constant.Const.*;
import static io.restassured.RestAssured.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Publisher controller test via Rest Assured
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PublisherControllerIT extends AbstractIntegrationTest{
    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    PublisherFacadeImpl publisherFacade;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Test
    public void shouldCreatePublisher() {
        PublisherCreateDto request = new PublisherCreateDto("Publisher name RA", "www.publisher-ra.org");
        Long id =
                given()
                        .auth()
                        .basic("admin", "admin")
                        .log().all()
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .post(contextPath+REST_MAP+REST_PUBLISHER_PATH)
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract()
                        .jsonPath()
                        .getLong("id");

        Publisher publisher = publisherRepository.findById(id).orElseThrow();
        assertEquals("Publisher name RA", publisher.getName());
        assertEquals("www.publisher-ra.org", publisher.getSite());
    }

    @Test
    public void shouldReadPublisher() {
        String checkName="ReadPublusherName";
        String checkSite="www.read-publisher-ra.org";
        PublisherCreateDto  publisherCreateDto = new PublisherCreateDto(checkName, checkSite);
        Long id = publisherFacade.create(publisherCreateDto).id();

        List<Long> ids = given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(contextPath+REST_MAP+REST_PUBLISHER_PATH+"?name="+checkName)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("id",Long.class);

        assertThat(ids).hasSize(1);
        Long getId = ids.getFirst();
        assertEquals(id, getId);
        Publisher publisher = publisherRepository.findById(id).orElseThrow();
        assertEquals(checkName, publisher.getName());
        assertEquals(checkSite, publisher.getSite());
    }

    @Test
    public void shouldUpdatePublisher() {
        PublisherCreateDto  publisherCreateDto = new PublisherCreateDto("Super Publisher name RA", "www.super-site.org");
        Long id = publisherFacade.create(publisherCreateDto).id();
        PublisherUpdateDto request = new PublisherUpdateDto( "Update Super Publisher name RA", "www.new-super-site.org");
        given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put(contextPath+REST_MAP+REST_PUBLISHER_PATH+"/"+id)
                .then()
                .log().all()
                .statusCode(202)
                .extract()
                .jsonPath();

        Publisher publisher = publisherRepository.findById(id).orElseThrow();
        assertEquals("Update Super Publisher name RA", publisher.getName());
        assertEquals("www.new-super-site.org", publisher.getSite());
    }

    @Test
    public void shouldDeletePublisher() {
        PublisherCreateDto publisherCreateDto = new PublisherCreateDto("Delete Publisher name RA", "www.delete-publisher.org");
        Long id = publisherFacade.create(publisherCreateDto).id();

        given()
                .auth()
                .basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(contextPath + REST_MAP + REST_PUBLISHER_PATH+"/"+id)
                .then()
                .log().all()
                .statusCode(204);

        assertFalse(publisherRepository.findById(id).isPresent());
    }
}
