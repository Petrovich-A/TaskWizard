package by.petrovich.taskwizard.controller;

import by.petrovich.taskwizard.BaseIntegrationTest;
import by.petrovich.taskwizard.dto.request.SignInRequestDto;
import by.petrovich.taskwizard.dto.response.JwtAuthenticationResponseDto;
import by.petrovich.taskwizard.dto.response.TaskCommentResponseDto;
import by.petrovich.taskwizard.dto.response.TaskResponseDto;
import by.petrovich.taskwizard.exception.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
class TaskControllerTest extends BaseIntegrationTest {
    private final Logger logger = LoggerFactory.getLogger(TaskControllerTest.class);
    private String baseUrl;
    protected final String signInUrl = "/api/v1/auth/sign-in";
    private HttpHeaders userHeaders;
    private HttpHeaders adminHeaders;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    void setUpAuth() {
        baseUrl = "/api/v1/task";
        logger.info("Base URL set: {}", baseUrl);

        userHeaders = new HttpHeaders();
        adminHeaders = new HttpHeaders();

        String passwordAuthUser = "D4v3*Secure*Pwd";
        String emailAuthUser = "dave@example.com";
        String jwtTokenUser = getJwtToken(emailAuthUser, passwordAuthUser);

        userHeaders.setBearerAuth(jwtTokenUser);

        String passwordAuthAdmin = "A1!s#9xPqZwef)l;jkwef19087";
        String emailAuthAdmin = "alice@example.com";
        String jwtTokenAdmin = getJwtToken(emailAuthAdmin, passwordAuthAdmin);

        adminHeaders.setBearerAuth(jwtTokenAdmin);
    }

    private String getJwtToken(String email, String password) {
        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .email(email)
                .password(password)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SignInRequestDto> signInEntity = new HttpEntity<>(signInRequestDto, headers);

        ResponseEntity<JwtAuthenticationResponseDto> signInResponse = restTemplate.postForEntity(
                signInUrl, signInEntity, JwtAuthenticationResponseDto.class
        );

        assertThat(signInResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        return signInResponse.getBody().getAccessToken();
    }

    @Test
    void findAll_WithValidUserRole_ShouldReturnOkAndPageOfTasks() throws JsonProcessingException {
        // Given:
        int page = 0;
        int size = 3;
        HttpEntity<Void> entity = new HttpEntity<>(userHeaders);

        // When:
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/tasks" + "?page=" + page + "&size=" + size,
                HttpMethod.GET,
                entity,
                String.class
        );

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        assertThat(jsonNode.path("content").size()).isGreaterThanOrEqualTo(0);
        assertThat(jsonNode.path("content").size()).isLessThanOrEqualTo(size);
        assertThat(jsonNode.path("totalElements").asLong()).isGreaterThanOrEqualTo(0);
        assertThat(jsonNode.path("size").asInt()).isEqualTo(size);
        assertThat(jsonNode.path("number").asInt()).isEqualTo(page);
        assertThat(jsonNode.path("content").get(0).path("title").asText()).isEqualTo("Setup project");
        assertThat(jsonNode.path("content").get(0).path("comments").size()).isGreaterThan(0);
    }

    @Test
    void find_WithValidUserRole_ShouldReturnOkAndTask() {
        // Given:
        List<TaskCommentResponseDto> expectedComments = List.of(
                TaskCommentResponseDto.builder()
                        .id(2L)
                        .comment("CI/CD pipeline configuration completed.")
                        .createdAt(LocalDateTime.parse("2025-05-01T10:00:00"))
                        .author("Bob")
                        .taskId(1L)
                        .build(),
                TaskCommentResponseDto.builder()
                        .id(1L)
                        .comment("Initial project setup looks good.")
                        .createdAt(LocalDateTime.parse("2025-05-01T09:15:00"))
                        .author("Alice")
                        .taskId(1L)
                        .build()
        );

        TaskResponseDto expectedTask = TaskResponseDto.builder()
                .id(1L)
                .title("Setup project")
                .description("Initialize the new project repository and configure basic CI/CD pipeline.")
                .createdAt(LocalDateTime.parse("2025-05-01T09:00:00"))
                .updatedAt(LocalDateTime.parse("2025-05-01T09:00:00"))
                .status("pending")
                .priority("Normal")
                .author("Alice")
                .assignee("Bob")
                .comments(expectedComments)
                .build();

        long taskId = 1L;
        HttpEntity<Void> entity = new HttpEntity<>(userHeaders);

        // When:
        ResponseEntity<TaskResponseDto> response = restTemplate.exchange(
                baseUrl + "/" + taskId,
                HttpMethod.GET,
                entity,
                TaskResponseDto.class
        );

        // Then:
        TaskResponseDto actualTask = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualTask).isNotNull();
        assertThat(actualTask).usingRecursiveComparison().isEqualTo(expectedTask);
        assertThat(actualTask.getId()).isEqualTo(expectedTask.getId());
        assertThat(actualTask.getTitle()).isEqualTo(expectedTask.getTitle());
        assertThat(actualTask.getComments()).hasSize(expectedComments.size());
    }

    @Test
    void find_WithoutAuth_ShouldReturn401() {
        long taskId = 1L;
        HttpEntity<Void> entity = new HttpEntity<>(new HttpHeaders());

        // When
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                baseUrl + "/" + taskId,
                HttpMethod.GET,
                entity,
                ErrorResponse.class
        );

        ErrorResponse expected = ErrorResponse.builder()
                .type("/errors/unauthorized")
                .title("unauthorized")
                .status(401)
                .detail("Access is denied. Authorization is required.")
                .instance("/api/v1/task/" + taskId)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody())
                .usingRecursiveComparison()
                .ignoringFields("timestamp")
                .isEqualTo(expected);
    }

    @Test
    @Transactional
    void deleteTask_WithAdminRole_ShouldDeleteSuccessfully() {
        Long taskId = 1L;
        HttpEntity<Void> entity = new HttpEntity<>(adminHeaders);

        // Check if task exists
        ResponseEntity<TaskResponseDto> checkResponse = restTemplate.exchange(
                baseUrl + "/" + taskId,
                HttpMethod.GET,
                entity,
                TaskResponseDto.class
        );
        assertThat(checkResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(checkResponse.getBody()).isNotNull();
        assertThat(checkResponse.getBody().getId()).isEqualTo(taskId);

        // DELETE
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                baseUrl + "/" + taskId,
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        logger.info("Task {} deleted successfully.", taskId);

        // Check task is deleted
        ResponseEntity<Void> verifyDeleted = restTemplate.exchange(
                baseUrl + "/" + taskId,
                HttpMethod.GET,
                entity,
                Void.class
        );
        assertThat(verifyDeleted.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}