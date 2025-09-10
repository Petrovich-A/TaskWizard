package by.petrovich.taskwizard.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a standardized error response conforming to RFC 7807,
 * for consistent error reporting in HTTP APIs.
 *
 * <p>This class provides structured details about API errors,
 * simplifying client-side handling.</p>
 *
 * <p>Key fields:</p>
 * <ul>
 *     <li><b>type</b>: URI for error type (e.g., "/errors/{type}" from {@link ErrorType} in lowercase).</li>
 *     <li><b>title</b>: Human-readable error name (underscores to spaces, lowercase).</li>
 *     <li><b>status</b>: HTTP status code, matching {@link ErrorType#getStatus()}.</li>
 *     <li><b>detail</b>: Specific error explanation, based on {@link ErrorType#getDescription()}.</li>
 *     <li><b>instance</b>: URI of the error occurrence (e.g., request URI).</li>
 *     <li><b>timestamp</b>: ISO 8601 date/time of error (e.g., "2024-01-01T12:00:00").</li>
 * </ul>
 *
 * <p>The {@code build} method constructs an instance automatically,
 * formatting type, title, and setting status, detail, instance, and current timestamp.</p>
 *
 * <p>This structure promotes uniformity in error reporting and eases client error handling,
 * leveraging the {@link ErrorType} enum for predefined error categories and messages.</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String type;

    private String title;

    private int status;

    private String detail;

    private String instance;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;

    public static ErrorResponse build(String type, int status, String detail, String instance) {
        String typeFormatted = "/errors/" + type.toLowerCase();
        String titleFormatted = type.replace("_", " ").toLowerCase();
        return ErrorResponse.builder()
                .type(typeFormatted)
                .title(titleFormatted)
                .status(status)
                .detail(detail)
                .instance(instance)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
