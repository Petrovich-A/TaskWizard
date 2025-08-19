package by.petrovich.taskwizard.service.impl;

import by.petrovich.taskwizard.dto.request.UserRequestDto;
import by.petrovich.taskwizard.dto.response.UserResponseDto;
import by.petrovich.taskwizard.exception.AppException;
import by.petrovich.taskwizard.exception.ErrorType;
import by.petrovich.taskwizard.mapper.UserMapper;
import by.petrovich.taskwizard.model.User;
import by.petrovich.taskwizard.repository.UserRepository;
import by.petrovich.taskwizard.service.UserService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static by.petrovich.taskwizard.exception.ErrorType.DATA_INTEGRITY_VIOLATION;
import static by.petrovich.taskwizard.exception.ErrorType.ENTITY_NOT_FOUND_ON_UPDATE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EntityManager entityManager;

    @Override
    public List<UserResponseDto> findAll(Sort sort) {
        List<User> users = userRepository.findAll(sort);
        return users.stream()
                .map(userMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto find(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorType.ENTITY_NOT_FOUND, User.class.getSimpleName()));
        return userMapper.toRequestDto(user);
    }

    @Override
    public UserResponseDto find(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorType.ENTITY_NOT_FOUND, User.class.getSimpleName()));
        return userMapper.toRequestDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto create(UserRequestDto userRequestDto) {
        try {
            User user = userMapper.toEntity(userRequestDto);
            User saved = userRepository.saveAndFlush(user);
            entityManager.refresh(saved);
            return userMapper.toRequestDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, User.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorType.ENTITY_NOT_FOUND, User.class.getSimpleName()));

        User updatedUser = userMapper.toEntityUpdate(userRequestDto, existingUser);
        User saved = userRepository.save(updatedUser);
        return userMapper.toRequestDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new AppException(ENTITY_NOT_FOUND_ON_UPDATE, User.class.getSimpleName());
        }

        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, User.class.getSimpleName());
        }
    }

}