package by.petrovich.taskwizard.service;

import by.petrovich.taskwizard.dto.request.SignInRequestDto;
import by.petrovich.taskwizard.dto.request.SignUpRequestDto;
import by.petrovich.taskwizard.dto.response.JwtAuthenticationResponseDto;
import by.petrovich.taskwizard.dto.response.UserResponseDto;

public interface AuthenticationService {
    UserResponseDto signUp(SignUpRequestDto signUpRequestDto);

    JwtAuthenticationResponseDto signIn(SignInRequestDto signInRequestDto);
}
