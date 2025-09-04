package by.petrovich.taskwizard.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for REST API, providing standardized error responses
 * for various types of exceptions. Uses ErrorResponse to format JSON outputs.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles AppException and building a structured error response.
     *
     * @param e       the AppException instance
     * @param request the HTTP request object
     * @return ResponseEntity with ErrorResponse
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException e, HttpServletRequest request) {
        logger.error("AppException occurred: {}", e.getMessage(), e);

        ErrorType errorType = e.getErrorType();

        String message = (e.getParams() != null)
                ? String.format(errorType.getDescription(), e.getParams())
                : errorType.getDescription();

        ErrorResponse errorResponse = ErrorResponse.build(
                errorType.name(),
                errorType.getStatus().value(),
                message,
                request.getRequestURI());

        return buildResponseEntity(errorType.getStatus(), errorResponse);
    }

    /**
     * Handles MethodArgumentNotValidException by extracting field validation errors
     * and building a structured error response.
     *
     * @param e       the MethodArgumentNotValidException instance
     * @param request the HTTP request object
     * @return ResponseEntity with ErrorResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {
        logger.error("MethodArgumentNotValidException occurred: {}", e.getMessage(), e);

        String message = buildValidationDetail(e);

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = ErrorResponse.build(
                ErrorType.INVALID_INPUT.name(),
                status.value(),
                message,
                request.getRequestURI());

        return buildResponseEntity(status, errorResponse);
    }

    /**
     * Handles general Exception by checking for AppException cause or falling back
     * to internal server error response.
     *
     * @param e       the Exception instance
     * @param request the HTTP request object
     * @return ResponseEntity with ErrorResponse
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        logger.error(ErrorType.DEFAULT.getDescription(), e);

        AppException cause = findCause(e, AppException.class);

        if (cause != null) {
            return handleAppException(cause, request);
        }
        ErrorResponse errorResponse = ErrorResponse.build(
                e.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                request.getRequestURI());

        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse);
    }

    /**
     * Recursively finds the cause of the exception in the throwable chain that matches the given type.
     *
     * @param t    the throwable to search (may be null)
     * @param type the class type to match
     * @param <T>  the type of the exception
     * @return the matching exception or null if not found
     */
    private static <T extends Throwable> T findCause(Throwable t, Class<T> type) {
        if (t == null) return null;
        if (type.isInstance(t)) return type.cast(t);
        Throwable cause = t.getCause();
        return (cause != null && cause != t) ? findCause(cause, type) : null;
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus status, ErrorResponse errorResponse) {
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    /**
     * Builds a detailed message from MethodArgumentNotValidException field errors.
     *
     * @param e the MethodArgumentNotValidException instance
     * @return formatted string of validation errors
     */
    private String buildValidationDetail(MethodArgumentNotValidException e) {
        Map<String, List<String>> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        return errors.entrySet().stream()
                .map(entry -> String.format("Field: '%s'\nMessages: '%s'",
                        entry.getKey(),
                        String.join(", ", entry.getValue())))
                .collect(Collectors.joining("\n\n"));
    }
}
