package by.petrovich.taskwizard.config;

import jakarta.annotation.PostConstruct;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvChecker {

    private final Environment env;

    public EnvChecker(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void checkEnv() {
        System.out.println("=== ENV CHECK ===");
        System.out.println("DATASOURCE_USERNAME: " + env.getProperty("DATASOURCE_USERNAME"));
        System.out.println("DATASOURCE_PASSWORD: " + env.getProperty("DATASOURCE_PASSWORD"));
        System.out.println("HIBERNATE_DDL_AUTO: " + env.getProperty("HIBERNATE_DDL_AUTO"));
        System.out.println("LIQUIBASE_ENABLED: " + env.getProperty("LIQUIBASE_ENABLED"));
        String secret = env.getProperty("JWT_SECRET_KEY", "NOT_SET");
        System.out.println("JWT Secret (first 10 chars): " +
                (secret.length() > 10 ? secret.substring(0, 10) + "..." : secret));
        System.out.println("SPRING_DATASOURCE_URL: " + env.getProperty("spring.datasource.url"));
        System.out.println("=== END CHECK ===");


    }
}