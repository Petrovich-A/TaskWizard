package by.petrovich.taskwizard.controller;

import by.petrovich.taskwizard.config.TestContainersConfig;
import by.petrovich.taskwizard.dto.request.SignInRequestDto;
import by.petrovich.taskwizard.dto.response.JwtAuthenticationResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Import(TestContainersConfig.class)
class TaskControllerTest {
    private final Logger logger = LoggerFactory.getLogger(TestContainersConfig.class);

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private HttpHeaders httpHeaders;

    @BeforeAll
    void setUpAuth() {
        baseUrl = "/api/v1/task";
        logger.info("Base URL set: {}", baseUrl);

        String passwordAuth = "D4v3*Secure*Pwd";
        String emailAuth = "dave@example.com";
        String jwtToken = getJwtToken(emailAuth, passwordAuth);

        httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwtToken);
    }

    private String getJwtToken(String email, String password) {
        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .email(email)
                .password(password)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SignInRequestDto> signInEntity = new HttpEntity<>(signInRequestDto, headers);

        String signInUrl = "/api/v1/auth/sign-in";
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
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

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

}