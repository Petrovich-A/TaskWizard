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

        HttpStatus status = switch (errorType) {
            case UNAUTHORIZED, INVALID_TOKEN, USER_NOT_FOUND_DURING_AUTHENTICATION -> HttpStatus.UNAUTHORIZED;
            case ENTITY_NOT_FOUND, ASSIGNEE_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ENTITY_ALREADY_EXISTS, DATA_INTEGRITY_VIOLATION -> HttpStatus.CONFLICT;
            case ENTITY_CREATION_FAILED, ENTITY_UPDATE_FAILED, ENTITY_DELETION_FAILED, MISSING_JWT_TOKEN -> HttpStatus.BAD_REQUEST;
            case ENTITY_DELETION_FORBIDDEN, TASK_MODIFICATION_FORBIDDEN -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        String message = String.format(errorType.getDescription(), e.getParams());

        ErrorResponse errorResponse = ErrorResponse.build(
                errorType.name(),
                status.value(),
                message,
                request.getRequestURI());

        return ResponseEntity.status(status)
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

        ErrorResponse errorResponse = ErrorResponse.build(
                ErrorType.DEFAULT.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorType.DEFAULT.getDescription(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

}
