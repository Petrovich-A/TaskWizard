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

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

        return ResponseEntity.status(errorType.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {
        logger.error("MethodArgumentNotValidException occurred: {}", e.getMessage(), e);

        Map<String, List<String>> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        String message = errors.entrySet().stream()
                .map(entry -> String.format("Field: '%s'\nMessages: '%s'",
                        entry.getKey(),
                        String.join(", ", entry.getValue())))
                .collect(Collectors.joining("\n\n"));

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = ErrorResponse.build(
                ErrorType.INVALID_INPUT.name(),
                httpStatus.value(),
                message,
                request.getRequestURI());

        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

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

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    private static <T extends Throwable> T findCause(Throwable t, Class<T> type) {
        while (t != null && t.getCause() != t) {
            if (type.isInstance(t)) return type.cast(t);
            t = t.getCause();
        }
        return null;
    }

}
