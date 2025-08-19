package by.petrovich.taskwizard.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException e, HttpServletRequest request) {
        logger.error("AppException occurred: {}", e.getMessage(), e);

        ErrorType errorType = e.getError();

        HttpStatus status = switch (errorType) {
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case ENTITY_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ENTITY_ALREADY_EXISTS, DATA_INTEGRITY_VIOLATION -> HttpStatus.CONFLICT;
            case ENTITY_CREATION_FAILED, ENTITY_UPDATE_FAILED, ENTITY_DELETION_FAILED -> HttpStatus.BAD_REQUEST;
            case ENTITY_DELETION_FORBIDDEN -> HttpStatus.FORBIDDEN;
            case TASK_MODIFICATION_FORBIDDEN -> HttpStatus.FORBIDDEN;
            case ASSIGNEE_NOT_FOUND -> HttpStatus.NOT_FOUND;
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

}
