package by.petrovich.taskwizard.service.impl;

import by.petrovich.taskwizard.dto.request.TaskRequestDto;
import by.petrovich.taskwizard.dto.response.TaskResponseDto;
import by.petrovich.taskwizard.exception.AppException;
import by.petrovich.taskwizard.exception.ErrorType;
import by.petrovich.taskwizard.mapper.TaskMapper;
import by.petrovich.taskwizard.model.Task;
import by.petrovich.taskwizard.model.TaskPriority;
import by.petrovich.taskwizard.model.TaskStatus;
import by.petrovich.taskwizard.model.User;
import by.petrovich.taskwizard.repository.TaskRepository;
import by.petrovich.taskwizard.service.TaskService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static by.petrovich.taskwizard.exception.ErrorType.DATA_INTEGRITY_VIOLATION;
import static by.petrovich.taskwizard.exception.ErrorType.ENTITY_NOT_FOUND;
import static by.petrovich.taskwizard.exception.ErrorType.ENTITY_NOT_FOUND_ON_UPDATE;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final EntityManager entityManager;

    @Override
    public Page<TaskResponseDto> findAll(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        return tasks.map(taskMapper::toResponseDto);
    }

    @Override
    public List<TaskResponseDto> findAll(Sort sort) {
        List<Task> Tasks = taskRepository.findAll(sort);
        return Tasks.stream().map(taskMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDto find(Long id) {
        return taskMapper.toResponseDto(taskRepository.findById(id)
                .orElseThrow(() -> new AppException(ENTITY_NOT_FOUND, Task.class.getSimpleName())));
    }

    @Override
    public Page<TaskResponseDto> findByAuthor(Long authorId, Pageable pageable) {
        Page<Task> tasksByAuthor = taskRepository.findByAuthor_Id(authorId, pageable);
        if (tasksByAuthor.isEmpty()) {
            throw new AppException(ENTITY_NOT_FOUND, Task.class.getSimpleName());
        }
        return tasksByAuthor.map(taskMapper::toResponseDto);
    }

    @Override
    public Page<TaskResponseDto> findByAssignee(Long assigneeId, Pageable pageable) {
        Page<Task> tasksByAuthor = taskRepository.findByAssignee_Id(assigneeId, pageable);
        if (tasksByAuthor.isEmpty()) {
            throw new AppException(ENTITY_NOT_FOUND, Task.class.getSimpleName());
        }
        return tasksByAuthor.map(taskMapper::toResponseDto);
    }

    @Override
    @Transactional
    public TaskResponseDto create(TaskRequestDto taskRequestDto) {
        try {
            Task taskToSave = taskMapper.toEntity(taskRequestDto);
            Task saved = taskRepository.saveAndFlush(taskToSave);
            return taskMapper.toResponseDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, Task.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public TaskResponseDto update(Long id, TaskRequestDto taskRequestDto) {
        try {
            Task task = getTaskOrThrow(id);
            taskMapper.toEntityUpdate(taskRequestDto, task);
            Task saved = taskRepository.saveAndFlush(task);
            return taskMapper.toResponseDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, Task.class.getSimpleName());
        } catch (EntityNotFoundException e) {
            throw new AppException(ENTITY_NOT_FOUND_ON_UPDATE, Task.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@taskServiceImpl.isAssignee(#id, authentication.principal.id)")
    public TaskResponseDto updateStatus(Long id, Long statusId) {
        try {
            Task task = getTaskOrThrow(id);

            task.setTaskStatus(getReference(TaskStatus.class, statusId));

            Task saved = taskRepository.saveAndFlush(task);
            return taskMapper.toResponseDto(saved);
        } catch (EntityNotFoundException e) {
            throw new AppException(ENTITY_NOT_FOUND, Task.class.getSimpleName());
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, Task.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public TaskResponseDto updatePriority(Long id, Long priorityId) {
        try {
            Task task = getTaskOrThrow(id);

            task.setTaskPriority(getReference(TaskPriority.class, priorityId));

            Task saved = taskRepository.saveAndFlush(task);
            return taskMapper.toResponseDto(saved);
        } catch (EntityNotFoundException e) {
            throw new AppException(ENTITY_NOT_FOUND, Task.class.getSimpleName());
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, Task.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public TaskResponseDto updateAuthor(Long id, Long userId) {
        try {
            Task task = getTaskOrThrow(id);

            task.setAuthor(getReference(User.class, userId));

            Task saved = taskRepository.saveAndFlush(task);
            return taskMapper.toResponseDto(saved);
        } catch (EntityNotFoundException e) {
            throw new AppException(ENTITY_NOT_FOUND, Task.class.getSimpleName());
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, Task.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public TaskResponseDto updateAssignee(Long id, Long userId) {
        try {
            Task task = getTaskOrThrow(id);

            task.setAssignee(getReference(User.class, userId));

            Task saved = taskRepository.saveAndFlush(task);
            return taskMapper.toResponseDto(saved);
        } catch (EntityNotFoundException e) {
            throw new AppException(ENTITY_NOT_FOUND, Task.class.getSimpleName());
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, Task.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new AppException(ENTITY_NOT_FOUND, Task.class.getSimpleName());
        }

        try {
            taskRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, Task.class.getSimpleName());
        }
    }

    public boolean isAssignee(Long taskId, Long userId) {
        Task task = getTaskOrThrow(taskId);
        if (task.getAssignee() == null || task.getAssignee().getId() == null) {
            throw new AppException(ErrorType.ASSIGNEE_NOT_FOUND);
        }
        if (!task.getAssignee().getId().equals(userId)) {
            throw new AppException(ErrorType.TASK_MODIFICATION_FORBIDDEN);
        }

        return true;
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new AppException(ENTITY_NOT_FOUND, Task.class.getSimpleName()));
    }

    private <T> T getReference(Class<T> clazz, Long id) {
        return entityManager.getReference(clazz, id);
    }

}
