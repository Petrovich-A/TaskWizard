package by.petrovich.taskwizard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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

}
