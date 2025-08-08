package by.petrovich.taskwizard.service;

import by.petrovich.taskwizard.dto.request.UserRequestDto;
import by.petrovich.taskwizard.dto.response.UserResponseDto;
import by.petrovich.taskwizard.exception.UserNotFoundException;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {
    List<UserResponseDto> findAll(Sort sort);

    UserResponseDto find(Long id) throws UserNotFoundException;

    UserResponseDto find(String email) throws UserNotFoundException;

    UserResponseDto create(UserRequestDto userRequestDto);

    void delete(Long id) throws UserNotFoundException;

    UserResponseDto update(Long id, UserRequestDto userRequestDto) throws UserNotFoundException;
}

