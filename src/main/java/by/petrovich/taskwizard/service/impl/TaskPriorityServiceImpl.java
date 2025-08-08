package by.petrovich.taskwizard.service.impl;

import by.petrovich.taskwizard.dto.request.TaskPriorityRequestDto;
import by.petrovich.taskwizard.dto.response.TaskPriorityResponseDto;
import by.petrovich.taskwizard.exception.TaskNotFoundException;
import by.petrovich.taskwizard.exception.TaskStatusNotFoundException;
import by.petrovich.taskwizard.mapper.TaskPriorityMapper;
import by.petrovich.taskwizard.model.TaskPriority;
import by.petrovich.taskwizard.repository.TaskPriorityRepository;
import by.petrovich.taskwizard.service.TaskPriorityService;
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
    public TaskPriorityResponseDto find(Long id) throws TaskNotFoundException {
        return taskPriorityMapper.toResponseDto(taskPriorityRepository.findById(id)
                .orElseThrow(() -> new TaskStatusNotFoundException("Task priority with id " + id + " not found")));
    }

    @Override
    @Transactional
    public TaskPriorityResponseDto create(TaskPriorityRequestDto taskPriorityRequestDto) {
        TaskPriority saved = taskPriorityRepository.save(taskPriorityMapper.toEntity(taskPriorityRequestDto));
        return taskPriorityMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) throws TaskNotFoundException {
        if (taskPriorityRepository.existsById(id)) {
            taskPriorityRepository.deleteById(id);
        } else {
            throw new TaskStatusNotFoundException("Task priority not found");
        }
    }

    @Override
    @Transactional
    public TaskPriorityResponseDto update(Long id, TaskPriorityRequestDto taskStatusRequestDto) throws TaskNotFoundException {
        Optional<TaskPriority> optionalPriority = taskPriorityRepository.findById(id);
        if (optionalPriority.isEmpty()) {
            throw new TaskNotFoundException("Task priority not found");
        } else {
            TaskPriority priorityUpdated = taskPriorityMapper.toEntityUpdate(taskStatusRequestDto, optionalPriority.get());
            TaskPriority saved = taskPriorityRepository.save(priorityUpdated);
            return taskPriorityMapper.toResponseDto(saved);
        }
    }

    @Override
    public List<TaskPriorityResponseDto> find(String name) {
        List<TaskPriority> taskStatuses = taskPriorityRepository.findByNameIgnoreCase(name);
        return taskStatuses.stream()
                .map(taskPriorityMapper::toResponseDto)
                .collect(Collectors.toList());
    }

}
