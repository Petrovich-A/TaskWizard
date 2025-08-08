package by.petrovich.taskwizard.service.impl;

import by.petrovich.taskwizard.dto.request.UserRequestDto;
import by.petrovich.taskwizard.dto.response.UserResponseDto;
import by.petrovich.taskwizard.exception.UserNotFoundException;
import by.petrovich.taskwizard.mapper.UserMapper;
import by.petrovich.taskwizard.model.User;
import by.petrovich.taskwizard.repository.UserRepository;
import by.petrovich.taskwizard.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserResponseDto> findAll(Sort sort) {
        List<User> users = userRepository.findAll(sort);
        return users.stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto find(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto find(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        User saved = userRepository.saveAndFlush(user);
        entityManager.refresh(saved);
        return userMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) throws UserNotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        User updatedUser = userMapper.toEntityUpdate(userRequestDto, existingUser);
        User saved = userRepository.save(updatedUser);
        return userMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) throws UserNotFoundException {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

}