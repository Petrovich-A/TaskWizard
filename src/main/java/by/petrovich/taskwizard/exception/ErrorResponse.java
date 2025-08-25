package by.petrovich.taskwizard.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static by.petrovich.taskwizard.constant.Constant.DATE_TIME_FORMAT_PATTERN;

/**
 * The {@code ErrorResponse} class represents a standardized error response
 * as defined by RFC 7807, which provides a consistent way to convey error
 * information in HTTP APIs. This class is designed to encapsulate various
 * attributes related to an error, making it easier for clients to understand
 * and handle errors returned by the API.
 *
 * <p>Each instance of {@code ErrorResponse} contains the following fields:</p>
 *
 * <ul>
 *     <li><b>type</b>: A URI reference that identifies the specific error type.
 *     This field is formatted as "/errors/{type}" where {type} is the error
 *     type in lowercase. This allows clients to programmatically retrieve
 *     documentation or further details about the error type.</li>
 *
 *     <li><b>title</b>: A short, human-readable summary of the error type.
 *     This field is formatted by replacing underscores in the error type
 *     with spaces and converting it to lowercase. It provides a quick
 *     understanding of the error encountered.</li>
 *
 *     <li><b>status</b>: The HTTP status code associated with the error.
 *     This integer value indicates the nature of the error, such as
 *     400 for Bad Request or 404 for Not Found.</li>
 *
 *     <li><b>detail</b>: A human-readable explanation specific to this
 *     occurrence of the error. This field provides additional context
 *     that can help clients understand the reason for the error.</li>
 *
 *     <li><b>instance</b>: A URI reference that identifies the specific
 *     occurrence of the error. This can be useful for debugging or
 *     tracking purposes, allowing clients to reference the specific
 *     error instance.</li>
 *
 *     <li><b>timestamp</b>: The date and time when the error occurred,
 *     represented as a {@code LocalDateTime}. This field is formatted
 *     according to the specified pattern (yyyy-MM-dd HH:mm:ss), allowing clients
 *     to know exactly when the error took place.</li>
 * </ul>
 *
 * <p>The class includes a static method {@code build} that facilitates
 * the creation of an {@code ErrorResponse} object. This method takes
 * parameters for the error type, status code, detail message, and
 * instance URI, and it automatically formats the type and title fields
 * before instantiating the object.</p>
 *
 * <p>By adhering to the RFC 7807 standard, the {@code ErrorResponse}
 * class enhances the clarity and consistency of error handling in
 * HTTP APIs, making it easier for developers to work with error
 * responses.</p>
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
