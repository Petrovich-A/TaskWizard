package by.petrovich.taskwizard.config;

import by.petrovich.taskwizard.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EnvTest extends BaseIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseIntegrationTest.class);

    @Autowired
    private Environment env;

    @Test
    void printEnvValues() {
        String secret = env.getProperty("jwt.secret.key", "NOT_SET");
        logger.info("JWT Secret (first 10 chars): " +
                (secret.length() > 10 ? secret.substring(0, 10) + "..." : secret));
    }
}
