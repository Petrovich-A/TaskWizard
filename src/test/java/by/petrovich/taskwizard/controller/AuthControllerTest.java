package by.petrovich.taskwizard.controller;

import by.petrovich.taskwizard.config.TestContainersConfig;
import by.petrovich.taskwizard.dto.request.SignInRequestDto;
import by.petrovich.taskwizard.dto.request.SignUpRequestDto;
import by.petrovich.taskwizard.dto.response.JwtAuthenticationResponseDto;
import by.petrovich.taskwizard.dto.response.UserResponseDto;
import by.petrovich.taskwizard.model.Role;
import by.petrovich.taskwizard.model.User;
import by.petrovich.taskwizard.repository.UserRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration")
@Import(TestContainersConfig.class)
class AuthControllerTest {
    private final Logger logger = LoggerFactory.getLogger(TestContainersConfig.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "/api/v1/auth";
        logger.info("Base URL set: {}", baseUrl);
    }

    @Test
    void signUp_WithValidData_ShouldCreateUserInDatabaseAndReturn201Created() {
        // Given
        String userName = "Grace";
        String email = "grace.user@demo.com";
        String password = "securePassword123";

        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .userName(userName)
                .email(email)
                .password(password)
                .build();

        logger.info("Attempting to register user with email: {}", email);

        // When
        ResponseEntity<UserResponseDto> actualResponse = restTemplate.postForEntity(
                baseUrl + "/sign-up",
                signUpRequestDto,
                UserResponseDto.class
        );

        logger.info("Sign-up request completed with status: {}", actualResponse.getStatusCode());

        // Then
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        UserResponseDto actualBody = actualResponse.getBody();
        assertThat(actualResponse.getBody()).isNotNull();

        assertThat(actualBody.getId()).isNotNull().isPositive();
        assertThat(actualBody.getName()).isEqualTo(userName);
        assertThat(actualBody.getEmail()).isEqualTo(email);

        assertThat(actualBody.getCreatedAt()).isNotNull();
        assertThat(actualBody.getUpdatedAt()).isNotNull();
        assertThat(actualBody.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(actualBody.getUpdatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(actualBody.getCreatedAt()).isEqualTo(actualBody.getUpdatedAt());

        assertThat(actualBody.getAuthoredTaskIds()).isNotNull();
        assertThat(actualBody.getAssignedTaskIds()).isNotNull();

        String roleUser = "ROLE_USER";
        assertThat(actualBody.getRoles())
                .isNotNull()
                .asInstanceOf(InstanceOfAssertFactories.collection(Role.class))
                .hasSize(1)
                .extracting(Role::getName)
                .containsExactly(roleUser);

        Optional<User> savedUser = userRepository.findByEmail(actualBody.getEmail());
        assertThat(savedUser).isPresent();

        logger.info("User successfully saved to the database: ID {}", savedUser.get().getId());

        assertThat(savedUser.get().getName()).isEqualTo(userName);
        assertThat(savedUser.get().getEmail()).isEqualTo(email);

        assertThat(savedUser.get().getPassword())
                .isNotEqualTo(password)
                .isNotEmpty();

        assertThat(savedUser.get().getRoles())
                .asInstanceOf(InstanceOfAssertFactories.collection(Role.class))
                .extracting(Role::getName)
                .contains(roleUser);

        userRepository.findByEmail(email).ifPresent(userRepository::delete);
        assertThat(userRepository.findByEmail(email))
                .as("User with email: {} should be delete form DB", email)
                .isEmpty();
    }

    @Test
    void signIn_WithValidCredentials_ShouldReturnUserDataAndJwtToken() {
        String userName = "John";
        String email = "john.test@demo.com";
        String password = "authPassword123";

        SignUpRequestDto signUpRequest = SignUpRequestDto.builder()
                .userName(userName)
                .email(email)
                .password(password)
                .build();

        logger.info("Trying to register user for sign-in test with email: {}", email);

        ResponseEntity<UserResponseDto> signUpResponse = restTemplate.postForEntity(
                baseUrl + "/sign-up",
                signUpRequest,
                UserResponseDto.class
        );

        logger.info("Pre-authorization user registration for sign-in test complete with status: {}", signUpResponse.getStatusCode());

        Optional<User> savedUser = userRepository.findByEmail(email);
        assertThat(savedUser).isPresent();
        Long expectedUserId = signUpResponse.getBody().getId();

        logger.info("User successfully saved to the database: ID {}", signUpResponse.getBody().getId());

        // Given:
        SignInRequestDto signInRequest = SignInRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        logger.info("Attempting to sign in with valid credentials for user: {}", email);

        // When:
        ResponseEntity<JwtAuthenticationResponseDto> actualResponse = restTemplate.postForEntity(
                baseUrl + "/sign-in",
                signInRequest,
                JwtAuthenticationResponseDto.class
        );

        logger.info("Sign-in request completed with status: {}", actualResponse.getStatusCode());

        // Then:
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        JwtAuthenticationResponseDto responseBody = actualResponse.getBody();
        assertThat(responseBody).isNotNull();

        logger.info("JWT token successfully generated for user: {}", responseBody.getEmail());

        assertThat(responseBody.getUserId()).isEqualTo(expectedUserId);
        assertThat(responseBody.getEmail()).isEqualTo(email);
        assertThat(responseBody.getName()).isEqualTo(userName);

        assertThat(responseBody.getAccessToken())
                .isNotNull()
                .isNotEmpty();

        assertThat(responseBody.getRoles())
                .isNotNull()
                .asInstanceOf(InstanceOfAssertFactories.collection(Role.class))
                .hasSize(1)
                .extracting(Role::getName)
                .containsExactly("ROLE_USER");

        String jwtToken = responseBody.getAccessToken().substring(7);
        assertThat(jwtToken).isNotEmpty();

        userRepository.findByEmail(email).ifPresent(userRepository::delete);
        assertThat(userRepository.findByEmail(email))
                .as("User with email: {} should be delete form DB", email)
                .isEmpty();
    }

    @Test
    void signUp_WithExistedEmail_ShouldReturn409Conflict() {
        String userName = "Bob";
        String email = "bob@example.com";
        String password = "B0b$Tr0ngP@ss";

        // Given
        SignUpRequestDto signUpRequestDtoWithExisted = SignUpRequestDto.builder()
                .userName(userName)
                .email(email)
                .password(password)
                .build();

        // When
        ResponseEntity<String> actualResponse = restTemplate.postForEntity(
                baseUrl + "/sign-up",
                signUpRequestDtoWithExisted,
                String.class
        );

        // Then
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(actualResponse.getBody()).contains("The entity User already exists.");
    }

    @Test
    void testDataConsistencyAfterRollback() {
        Optional<User> savedUser = userRepository.findByEmail("grace.user@demo.com");
        assertThat(savedUser).isNotPresent();
    }

}