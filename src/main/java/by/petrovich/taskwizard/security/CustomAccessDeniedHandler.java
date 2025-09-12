package by.petrovich.taskwizard.security;

import by.petrovich.taskwizard.exception.ErrorResponse;
import by.petrovich.taskwizard.exception.ErrorType;
import by.petrovich.taskwizard.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        logger.debug("Access denied for request [{}] to URL '{}'. Exception class: {}, Message: {}",
                request.getMethod(), request.getRequestURI(), accessDeniedException.getClass().getSimpleName(),
                accessDeniedException.getMessage());

        ErrorResponse errorResponse = ErrorResponse.build(
                ErrorType.FORBIDDEN_ACCESS.name(),
                ErrorType.FORBIDDEN_ACCESS.getStatus().value(),
                ErrorType.FORBIDDEN_ACCESS.getDescription(),
                request.getRequestURI());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
