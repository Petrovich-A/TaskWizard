package by.petrovich.taskwizard.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = TestContainersConfig.class)
@Testcontainers
public class TestContainersTest {

    @Test
    void checkContainerStartup() {
        PostgreSQLContainer<?> postgres = TestContainersConfig.getPostgres();
        postgres.start();
        assertTrue(postgres.isRunning(), "Container is running");
    }
}
