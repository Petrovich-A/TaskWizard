package by.petrovich.taskwizard.security;

import by.petrovich.taskwizard.exception.ErrorResponse;
import by.petrovich.taskwizard.exception.ErrorType;
import by.petrovich.taskwizard.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        logger.debug("Authentication failed in AuthenticationEntryPoint for request [{}] to URL '{}'. Exception class: {}, Message: {}",
                request.getMethod(), request.getRequestURI(), authException.getClass().getSimpleName(), authException.getMessage(), authException);

        ErrorResponse errorResponse = ErrorResponse.build(
                ErrorType.UNAUTHORIZED.name(),
                ErrorType.UNAUTHORIZED.getStatus().value(),
                ErrorType.UNAUTHORIZED.getDescription(),
                request.getRequestURI());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
