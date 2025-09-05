package by.petrovich.taskwizard.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static by.petrovich.taskwizard.constant.Constant.DATE_TIME_FORMAT_PATTERN;

@RestController
public class HealthController {
    private final Instant startTime = Instant.now();
    private final JdbcTemplate jdbcTemplate;

    @Value("${spring.application.name}")
    private String serviceName;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();

        String status = "UP";

        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            response.put("database", "UP");
        } catch (Exception ex) {
            response.put("database", "DOWN");
            status = "DOWN";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN);
        response.put("status", status);
        response.put("timestamp", LocalDateTime.now().format(dateTimeFormatter));
        response.put("service", serviceName);
        response.put("uptime_seconds", Duration.between(startTime, Instant.now()).getSeconds());

        if ("UP".equals(status)) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(503).body(response);
        }
    }
}
