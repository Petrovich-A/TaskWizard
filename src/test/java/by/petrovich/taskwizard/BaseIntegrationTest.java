package by.petrovich.taskwizard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseIntegrationTest.class);

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withExposedPorts(5432)
            .withLogConsumer(outputFrame ->
                    logger.info("POSTGRES: {}", outputFrame.getUtf8String().trim()));

    static {
        logger.info("Initializing PostgreSQL container...");
        postgres.start();
        logger.info("Container PostgreSQL run successfully");
        logger.info("JDBC URL: {}", postgres.getJdbcUrl());
        logger.info("Host: {}", postgres.getHost());
        logger.info("Mapped port: {}", postgres.getMappedPort(5432));
        logger.info("Username: {}", postgres.getUsername());
        logger.info("Password: {}", postgres.getPassword());
    }

    public static PostgreSQLContainer<?> getPostgres() {
        return postgres;
    }


    /**
     * Generic helper to send HTTP POST request for creating an entity.
     * <p>
     * Sends the given request entity to the specified URL and expects a response of type T.
     * Asserts that response body is not null.
     * Includes custom failure messages for better debugging.
     *
     * @param <T>          Response type (e.g., TaskResponseDto.class)
     * @param <E>          Request entity type (e.g., TaskRequestDto)
     * @param url          Full URL of the POST endpoint
     * @param entity       HttpEntity containing request body and headers
     * @param responseType Class of the expected response type
     * @return ResponseEntity containing the created entity
     */
    protected <T, E> ResponseEntity<T> sendPostRequest(String url, HttpEntity<E> entity, Class<T> responseType) {
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        assertThat(response.getBody())
                .withFailMessage("Body is null for creation at URL: %s", url)
                .isNotNull();
        return response;
    }

}
