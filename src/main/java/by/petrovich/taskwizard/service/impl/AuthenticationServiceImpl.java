package by.petrovich.taskwizard.service.impl;

import by.petrovich.taskwizard.dto.request.SignInRequestDto;
import by.petrovich.taskwizard.dto.request.SignUpRequestDto;
import by.petrovich.taskwizard.dto.request.UserRequestDto;
import by.petrovich.taskwizard.dto.response.JwtAuthenticationResponseDto;
import by.petrovich.taskwizard.dto.response.UserResponseDto;
import by.petrovich.taskwizard.exception.AppException;
import by.petrovich.taskwizard.exception.ErrorType;
import by.petrovich.taskwizard.mapper.UserMapper;
import by.petrovich.taskwizard.model.Role;
import by.petrovich.taskwizard.model.User;
import by.petrovich.taskwizard.repository.RoleRepository;
import by.petrovich.taskwizard.repository.UserRepository;
import by.petrovich.taskwizard.security.CustomUserDetails;
import by.petrovich.taskwizard.security.CustomUserDetailsService;
import by.petrovich.taskwizard.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new AppException(ErrorType.ENTITY_ALREADY_EXISTS, User.class.getSimpleName());
        }
        Optional<Role> role = roleRepository.findByNameIgnoreCase("ROLE_USER");
        if (role.isEmpty()) {
            throw new AppException(ErrorType.ENTITY_NOT_FOUND, Role.class.getSimpleName());
        }
        UserRequestDto userRequestDto = userMapper.toRequestDto(signUpRequestDto);
        userRequestDto.setRoles(Collections.singleton(role.get()));

        String hashedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        userRequestDto.setPassword(hashedPassword);

        return userService.create(userRequestDto);
    }

    public JwtAuthenticationResponseDto signIn(SignInRequestDto signInRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequestDto.getEmail(),
                signInRequestDto.getPassword()
        ));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(signInRequestDto.getEmail());

        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                authentication.getCredentials(),
                userDetails.getAuthorities()
        );

        Set<Role> roles = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> new Role(grantedAuthority.getAuthority()))
                .collect(Collectors.toSet());

        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getId();

        String jwt = jwtTokenProvider.generateToken(authentication);

        return JwtAuthenticationResponseDto.builder()
                .userId(userId)
                .accessToken(jwt)
                .email(signInRequestDto.getEmail())
                .name(customUserDetails.getUsername())
                .roles(roles)
                .build();
    }

}
