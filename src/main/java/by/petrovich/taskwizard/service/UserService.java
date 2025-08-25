package by.petrovich.taskwizard.service;

import by.petrovich.taskwizard.dto.request.UserRequestDto;
import by.petrovich.taskwizard.dto.response.UserResponseDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {
    List<UserResponseDto> findAll(Sort sort);

    UserResponseDto find(Long id);

    UserResponseDto find(String email);

    UserResponseDto create(UserRequestDto userRequestDto);

    void delete(Long id);

    UserResponseDto update(Long id, UserRequestDto userRequestDto);
}

