package by.petrovich.taskwizard.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TaskErrorResponse> handleException(Exception e) {
        logger.error("Unexpected error occurred", e);
        TaskErrorResponse taskErrorResponse = new TaskErrorResponse(INTERNAL_SERVER_ERROR.value(), "Internal Server Error", e.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).contentType(APPLICATION_JSON).body(taskErrorResponse);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<TaskErrorResponse> handleReminderNotFoundException(TaskNotFoundException e) {
        logger.error("Reminder not found", e);
        TaskErrorResponse taskErrorResponse = new TaskErrorResponse(NOT_FOUND.value(), "Not Found", e.getMessage());
        return ResponseEntity.status(NOT_FOUND).contentType(APPLICATION_JSON).body(taskErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TaskErrorResponse> handleArgumentNotValidExceptions(MethodArgumentNotValidException e) {
        logger.error("Invalid argument", e);
        TaskErrorResponse taskErrorResponse = new TaskErrorResponse(BAD_REQUEST.value(), "Bad Request", e.getMessage());
        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON).body(taskErrorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<TaskErrorResponse> handleValidationExceptions(ConstraintViolationException e) {
        logger.error("find.id: received negative or invalid id in url", e);
        TaskErrorResponse taskErrorResponse = new TaskErrorResponse(BAD_REQUEST.value(), "Bad Request", e.getMessage());
        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON).body(taskErrorResponse);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<TaskErrorResponse> handleNumberFormatExceptionExceptions(NumberFormatException e) {
        logger.error("Invalid ID format", e);
        TaskErrorResponse taskErrorResponse = new TaskErrorResponse(BAD_REQUEST.value(), "Bad Request", e.getMessage());
        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON).body(taskErrorResponse);
    }
}
