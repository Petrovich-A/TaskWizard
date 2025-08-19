package by.petrovich.taskwizard.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Test
    void testHandleAppException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        AppException appException = new AppException(ErrorType.UNAUTHORIZED);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/resource");

        ResponseEntity<ErrorResponse> actualResponseEntity = handler.handleAppException(appException, request);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());


        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .detail("Access is denied. Authorization is required.")
                .instance("/api/v1/resource")
                .status(HttpStatus.UNAUTHORIZED.value())
                .title("unauthorized")
                .type("/errors/unauthorized")
                .timestamp(localDateTime)
                .build();

        ResponseEntity<ErrorResponse> expectedResponseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(expectedErrorResponse);

        assertNotNull(actualResponseEntity);
        assertEquals(expectedResponseEntity.getStatusCode(), actualResponseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getDetail(), actualResponseEntity.getBody().getDetail());
        assertEquals(expectedResponseEntity.getBody().getInstance(), actualResponseEntity.getBody().getInstance());
        assertEquals(expectedResponseEntity.getBody().getStatus(), actualResponseEntity.getBody().getStatus());
        assertEquals(expectedResponseEntity.getBody().getTitle(), actualResponseEntity.getBody().getTitle());
        assertEquals(expectedResponseEntity.getBody().getType(), actualResponseEntity.getBody().getType());
        assertEquals(expectedResponseEntity.getBody().getTimestamp().toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS),
                actualResponseEntity.getBody().getTimestamp().toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS));
    }

}