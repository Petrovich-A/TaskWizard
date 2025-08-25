package by.petrovich.taskwizard.service.impl;

import by.petrovich.taskwizard.dto.request.TaskPriorityRequestDto;
import by.petrovich.taskwizard.dto.response.TaskPriorityResponseDto;
import by.petrovich.taskwizard.exception.AppException;
import by.petrovich.taskwizard.mapper.TaskPriorityMapper;
import by.petrovich.taskwizard.model.TaskPriority;
import by.petrovich.taskwizard.repository.TaskPriorityRepository;
import by.petrovich.taskwizard.service.TaskPriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.petrovich.taskwizard.exception.ErrorType.DATA_INTEGRITY_VIOLATION;
import static by.petrovich.taskwizard.exception.ErrorType.ENTITY_DELETION_FAILED;
import static by.petrovich.taskwizard.exception.ErrorType.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskPriorityServiceImpl implements TaskPriorityService {
    private final TaskPriorityRepository taskPriorityRepository;
    private final TaskPriorityMapper taskPriorityMapper;


    @Override
    public List<TaskPriorityResponseDto> findAll(Sort sort) {
        List<TaskPriority> taskPriorities = taskPriorityRepository.findAll(sort);
        return taskPriorities.stream().map(taskPriorityMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskPriorityResponseDto find(Long id) {
        return taskPriorityMapper.toResponseDto(taskPriorityRepository.findById(id)
                .orElseThrow(() -> new AppException(ENTITY_NOT_FOUND, TaskPriority.class.getSimpleName())));
    }

    @Override
    public List<TaskPriorityResponseDto> find(String name) {
        List<TaskPriority> taskStatuses = taskPriorityRepository.findByNameIgnoreCase(name);
        return taskStatuses.stream()
                .map(taskPriorityMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskPriorityResponseDto create(TaskPriorityRequestDto taskPriorityRequestDto) {
        try {
            TaskPriority saved = taskPriorityRepository.save(taskPriorityMapper.toEntity(taskPriorityRequestDto));
            return taskPriorityMapper.toResponseDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, TaskPriority.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public TaskPriorityResponseDto update(Long id, TaskPriorityRequestDto taskStatusRequestDto) {
        try {
            TaskPriority taskPriority = taskPriorityRepository.findById(id)
                    .orElseThrow(() -> new AppException(ENTITY_NOT_FOUND, TaskPriority.class.getSimpleName()));

            TaskPriority priorityUpdated = taskPriorityMapper.toEntityUpdate(taskStatusRequestDto, taskPriority);
            TaskPriority saved = taskPriorityRepository.save(priorityUpdated);
            return taskPriorityMapper.toResponseDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, TaskPriority.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<TaskPriority> taskPriority = taskPriorityRepository.findById(id);
        if (taskPriority.isEmpty()) {
            throw new AppException(ENTITY_NOT_FOUND, TaskPriority.class.getSimpleName());
        }

        try {
            taskPriorityRepository.delete(taskPriority.get());
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, TaskPriority.class.getSimpleName());
        } catch (Exception e) {
            throw new AppException(ENTITY_DELETION_FAILED, TaskPriority.class.getSimpleName());
        }
    }

}
