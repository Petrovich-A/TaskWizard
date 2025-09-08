package by.petrovich.taskwizard.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfig {
    private static final Logger logger = LoggerFactory.getLogger(TestContainersConfig.class);

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withExposedPorts(5432)
            .withReuse(false)
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
