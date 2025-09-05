package by.petrovich.taskwizard.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static by.petrovich.taskwizard.constant.Constant.DATE_TIME_FORMAT_PATTERN;

/**
 * Represents a standardized error response conforming to RFC 7807,
 * designed to provide consistent and clear error information in HTTP APIs.
 *
 * <p>This class encapsulates details about an error occurring during API
 * processing, making it easier for clients to interpret and handle errors.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *     <li><b>type</b>: URI identifying the error type, formatted as "/errors/{type}".
 *         Typically derived from an {@link ErrorType} enum name in lowercase.</li>
 *     <li><b>title</b>: Human-readable, simplified error type name (underscores replaced by spaces, lowercase).</li>
 *     <li><b>status</b>: HTTP status code associated with the error, matching {@link ErrorType#getStatus()}.</li>
 *     <li><b>detail</b>: Specific explanation of this error occurrence, often formatted from
 *         {@link ErrorType#getDescription()} with relevant parameters.</li>
 *     <li><b>instance</b>: URI identifying the particular occurrence of this error (e.g., request URI).</li>
 *     <li><b>timestamp</b>: Date and time when the error happened, formatted as "yyyy-MM-dd HH:mm:ss".</li>
 * </ul>
 *
 * <p>The static {@code build} method simplifies constructing an instance by formatting
 * {@code type} and {@code title} from the error type, setting the status, detail message,
 * instance URI, and timestamp automatically.</p>
 *
 * <p>This structure promotes uniformity in error reporting and eases client error handling,
 * leveraging the {@link ErrorType} enum for predefined error categories and messages.</p>
 */
@Data
@Builder
public class ErrorResponse {
    private String type;

    private String title;

    private int status;

    private String detail;

    private String instance;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_PATTERN)
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
