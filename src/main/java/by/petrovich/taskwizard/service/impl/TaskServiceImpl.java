package by.petrovich.taskwizard.service.impl;

import by.petrovich.taskwizard.dto.request.TaskRequestDto;
import by.petrovich.taskwizard.dto.response.TaskResponseDto;
import by.petrovich.taskwizard.exception.TaskNotFoundException;
import by.petrovich.taskwizard.exception.TaskPriorityNotFoundException;
import by.petrovich.taskwizard.exception.TaskStatusNotFoundException;
import by.petrovich.taskwizard.exception.UserNotFoundException;
import by.petrovich.taskwizard.mapper.TaskMapper;
import by.petrovich.taskwizard.model.Task;
import by.petrovich.taskwizard.model.TaskPriority;
import by.petrovich.taskwizard.model.TaskStatus;
import by.petrovich.taskwizard.model.User;
import by.petrovich.taskwizard.repository.TaskRepository;
import by.petrovich.taskwizard.service.TaskService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final EntityManager entityManager;

    @Override
    public Page<TaskResponseDto> findAll(Pageable pageable) {
        Page<Task> Tasks = taskRepository.findAll(pageable);
        return Tasks.map(taskMapper::toResponseDto);
    }

    @Override
    public List<TaskResponseDto> findAll(Sort sort) {
        List<Task> Tasks = taskRepository.findAll(sort);
        return Tasks.stream().map(taskMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDto find(Long id) throws TaskNotFoundException {
        return taskMapper.toResponseDto(taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found")));
    }

    @Override
    @Transactional
    public TaskResponseDto create(TaskRequestDto taskRequestDto) {
        Task saved = taskRepository.saveAndFlush(taskMapper.toEntity(taskRequestDto));
        return taskMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) throws TaskNotFoundException {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    @Override
    @Transactional
    public TaskResponseDto update(Long id, TaskRequestDto taskRequestDto) throws TaskNotFoundException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        taskMapper.toEntityUpdate(taskRequestDto, task);

        task.setTaskStatus(getReference(TaskStatus.class, taskRequestDto.getTaskStatusId()));
        task.setTaskPriority(getReference(TaskPriority.class, taskRequestDto.getTaskPriorityId()));
        task.setAuthor(getReference(User.class, taskRequestDto.getAuthorId()));
        task.setAssignee(getReference(User.class, taskRequestDto.getAssigneeId()));

        Task saved = taskRepository.saveAndFlush(task);
        return taskMapper.toResponseDto(saved);
    }

    @Override
    public TaskResponseDto updateTaskAuthor(Long taskId, Long userId) throws TaskNotFoundException, UserNotFoundException {
        return null;
    }

    @Override
    public TaskResponseDto updateTaskStatus(Long taskId, Long statusId) throws TaskNotFoundException, TaskStatusNotFoundException {
        return null;
    }

    @Override
    public TaskResponseDto updateTaskPriority(Long taskId, Long priorityId) throws TaskNotFoundException, TaskPriorityNotFoundException {
        return null;
    }

    private <T> T getReference(Class<T> clazz, Long id) {
        return entityManager.getReference(clazz, id);
    }

}
