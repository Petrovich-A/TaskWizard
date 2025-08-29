package by.petrovich.taskwizard.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EnvTest {

    @Autowired
    private Environment env;

    @Test
    void printEnvValues() {
        String secret = env.getProperty("jwt.secret.key", "NOT_SET");
        System.out.println("JWT Secret (first 10 chars): " +
                (secret.length() > 10 ? secret.substring(0, 10) + "..." : secret));
    }
}
