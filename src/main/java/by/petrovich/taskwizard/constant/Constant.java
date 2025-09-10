package by.petrovich.taskwizard.constant;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

/**
 * Utility class containing commonly used formatters for date and time in ISO 8601 standard.
 * These formatters are used across the application for consistency in parsing and formatting temporal values.
 */
@UtilityClass
public class Constant {

    /**
     * Formatter for local date-time without time zone offset.
     * Pattern: {@code 2024-01-01T12:00:00}
     * Use for: Local times in internal calculations or logs where timezone is not required.
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Formatter for date-time with time zone offset.
     * Pattern: {@code 2024-01-01T12:00:00+03:00}
     * Use for: Times that include timezone information for better precision in data exchange.
     */
    public static final DateTimeFormatter DATE_TIME_WITH_OFFSET_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Formatter for Instant in UTC.
     * Pattern: {@code 2024-01-01T12:00:00Z}
     * Use for: Universal timestamps, storage, or API responses where UTC is enforced.
     */
    public static final DateTimeFormatter INSTANT_FORMATTER = DateTimeFormatter.ISO_INSTANT;
}
