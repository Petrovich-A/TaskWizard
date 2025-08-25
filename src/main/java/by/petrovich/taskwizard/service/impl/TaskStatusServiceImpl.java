package by.petrovich.taskwizard.service.impl;

import by.petrovich.taskwizard.dto.request.TaskStatusRequestDto;
import by.petrovich.taskwizard.dto.response.TaskStatusResponseDto;
import by.petrovich.taskwizard.exception.AppException;
import by.petrovich.taskwizard.exception.ErrorType;
import by.petrovich.taskwizard.mapper.TaskStatusMapper;
import by.petrovich.taskwizard.model.Task;
import by.petrovich.taskwizard.model.TaskStatus;
import by.petrovich.taskwizard.repository.TaskStatusRepository;
import by.petrovich.taskwizard.service.TaskStatusService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static by.petrovich.taskwizard.exception.ErrorType.DATA_INTEGRITY_VIOLATION;
import static by.petrovich.taskwizard.exception.ErrorType.ENTITY_NOT_FOUND;
import static by.petrovich.taskwizard.exception.ErrorType.ENTITY_NOT_FOUND_ON_UPDATE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskStatusServiceImpl implements TaskStatusService {
    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusMapper taskStatusMapper;


    @Override
    public List<TaskStatusResponseDto> findAll(Sort sort) {
        List<TaskStatus> TaskStatuses = taskStatusRepository.findAll(sort);
        return TaskStatuses.stream().map(taskStatusMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskStatusResponseDto find(Long id) {
        return taskStatusMapper.toResponseDto(taskStatusRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorType.ENTITY_NOT_FOUND, TaskStatus.class.getSimpleName())));
    }

    @Override
    public List<TaskStatusResponseDto> find(String name) {
        List<TaskStatus> taskStatuses = taskStatusRepository.findByNameIgnoreCase(name);
        return taskStatuses.stream()
                .map(taskStatusMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskStatusResponseDto create(TaskStatusRequestDto taskStatusRequestDto) {
        try {
            TaskStatus saved = taskStatusRepository.save(taskStatusMapper.toEntity(taskStatusRequestDto));
            return taskStatusMapper.toResponseDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, TaskStatus.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public TaskStatusResponseDto update(Long id, TaskStatusRequestDto taskStatusRequestDto) {
        try {
            TaskStatus taskStatus = taskStatusRepository.findById(id).orElseThrow(() ->
                    new AppException(ENTITY_NOT_FOUND, TaskStatus.class.getSimpleName()));
            TaskStatus statusUpdated = taskStatusMapper.toEntityUpdate(taskStatusRequestDto, taskStatus);
            TaskStatus saved = taskStatusRepository.save(statusUpdated);
            return taskStatusMapper.toResponseDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, Task.class.getSimpleName());
        } catch (
                EntityNotFoundException e) {
            throw new AppException(ENTITY_NOT_FOUND_ON_UPDATE, Task.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!taskStatusRepository.existsById(id)) {
            throw new AppException(ENTITY_NOT_FOUND, TaskStatus.class.getSimpleName());
        }

        try {
            taskStatusRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, TaskStatus.class.getSimpleName());
        }
    }

}
