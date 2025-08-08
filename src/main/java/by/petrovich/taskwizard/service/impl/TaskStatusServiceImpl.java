package by.petrovich.taskwizard.service.impl;

import by.petrovich.taskwizard.dto.request.TaskStatusRequestDto;
import by.petrovich.taskwizard.dto.response.TaskStatusResponseDto;
import by.petrovich.taskwizard.exception.TaskNotFoundException;
import by.petrovich.taskwizard.exception.TaskStatusNotFoundException;
import by.petrovich.taskwizard.mapper.TaskStatusMapper;
import by.petrovich.taskwizard.model.TaskStatus;
import by.petrovich.taskwizard.repository.TaskStatusRepository;
import by.petrovich.taskwizard.service.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public TaskStatusResponseDto find(Long id) throws TaskNotFoundException {
        return taskStatusMapper.toResponseDto(taskStatusRepository.findById(id)
                .orElseThrow(() -> new TaskStatusNotFoundException("Task status with id " + id + " not found")));
    }

    @Override
    @Transactional
    public TaskStatusResponseDto create(TaskStatusRequestDto taskStatusRequestDto) {
        TaskStatus saved = taskStatusRepository.save(taskStatusMapper.toEntity(taskStatusRequestDto));
        return taskStatusMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) throws TaskNotFoundException {
        if (taskStatusRepository.existsById(id)) {
            taskStatusRepository.deleteById(id);
        } else {
            throw new TaskStatusNotFoundException("Task status not found");
        }
    }

    @Override
    @Transactional
    public TaskStatusResponseDto update(Long id, TaskStatusRequestDto taskStatusRequestDto) throws TaskNotFoundException {
        Optional<TaskStatus> optionalStatus = taskStatusRepository.findById(id);
        if (optionalStatus.isEmpty()) {
            throw new TaskNotFoundException("Task status not found");
        } else {
            TaskStatus statusUpdated = taskStatusMapper.toEntityUpdate(taskStatusRequestDto, optionalStatus.get());
            TaskStatus saved = taskStatusRepository.save(statusUpdated);
            return taskStatusMapper.toResponseDto(saved);
        }
    }

    @Override
    public List<TaskStatusResponseDto> find(String name) {
        List<TaskStatus> taskStatuses = taskStatusRepository.findByNameIgnoreCase(name);
        return taskStatuses.stream()
                .map(taskStatusMapper::toResponseDto)
                .collect(Collectors.toList());
    }

}
