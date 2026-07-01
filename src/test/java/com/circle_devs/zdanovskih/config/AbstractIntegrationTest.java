package com.circle_devs.zdanovskih.config;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Configuration for testcontainers
 */

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {
    @LocalServerPort
    protected int port;
    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
    @ServiceConnection
    static PostgreSQLContainer<?> postgres;

    static {
        postgres =  new PostgreSQLContainer<>("postgres:16-alpine");
    }


}
