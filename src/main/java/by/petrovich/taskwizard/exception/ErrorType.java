package by.petrovich.taskwizard.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Enum representing predefined error types for standardized error responses in the application.
 * Each error type includes a descriptive message template and an associated HTTP status code,
 * facilitating consistent error handling and reporting via the {@link ErrorResponse} class.
 *
 * <p>This enum is designed to align with RFC 7807 for problem details in HTTP APIs,
 * providing reusable error definitions that can be used to build {@link ErrorResponse} instances.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * ErrorResponse error = ErrorResponse.build(
 *     ErrorType.ENTITY_NOT_FOUND.name(),
 *     ErrorType.ENTITY_NOT_FOUND.getStatus().value(),
 *     String.format(ErrorType.ENTITY_NOT_FOUND.getDescription(), "User"),
 *     "/api/users/123"
 * );
 * }</pre>
 *
 * <p>Whenever a description template contains placeholders (e.g., "%s"),
 * they should be filled using {@link String#format} with relevant parameters.</p>
 *
 * @see ErrorResponse
 * @see <a href="https://tools.ietf.org/html/rfc7807">RFC 7807: Problem Details for HTTP APIs</a>
 */

@Getter
public enum ErrorType {
    DEFAULT("An internal error occurred. Please try again later.", INTERNAL_SERVER_ERROR),
    ENTITY_NOT_FOUND("The requested %s was not found.", NOT_FOUND),
    ENTITY_NOT_FOUND_ON_UPDATE("The %s to update was not found.", NOT_FOUND),
    DATA_INTEGRITY_VIOLATION("%s with the same unique property already exists.", CONFLICT),
    ENTITY_ALREADY_EXISTS("The entity %s already exists.", CONFLICT),
    ENTITY_CREATION_FAILED("Failed to create %s.", INTERNAL_SERVER_ERROR),
    ENTITY_UPDATE_FAILED("Failed to update %s.", INTERNAL_SERVER_ERROR),
    ENTITY_DELETION_FAILED("Failed to delete %s.", INTERNAL_SERVER_ERROR),
    ENTITY_DELETION_FORBIDDEN("Deletion of %s is forbidden.", FORBIDDEN),
    ASSIGNEE_NOT_FOUND("The task does not have an assigned user.", NOT_FOUND),
    TASK_MODIFICATION_FORBIDDEN("You do not have permission to modify this task.", FORBIDDEN),
    INVALID_INPUT("The provided data is invalid and does not meet the required criteria.", BAD_REQUEST),
    USER_NOT_FOUND_DURING_AUTHENTICATION("Authentication failed: User not found with email: %s.", NOT_FOUND),
    UNAUTHORIZED("Access is denied. Authorization is required.", HttpStatus.UNAUTHORIZED),
    BAD_CREDENTIALS("Authentication failed. Please check your credentials.", HttpStatus.UNAUTHORIZED);

    private final String description;
    private final HttpStatus status;

    ErrorType(String description, HttpStatus status) {
        this.description = description;
        this.status = status;
    }
}
