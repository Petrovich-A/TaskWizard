package by.petrovich.taskwizard.service;

import by.petrovich.taskwizard.dto.request.TaskRequestDto;
import by.petrovich.taskwizard.dto.response.TaskResponseDto;
import by.petrovich.taskwizard.exception.TaskNotFoundException;
import by.petrovich.taskwizard.exception.TaskPriorityNotFoundException;
import by.petrovich.taskwizard.exception.TaskStatusNotFoundException;
import by.petrovich.taskwizard.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TaskService {
    Page<TaskResponseDto> findAll(Pageable pageable);

    List<TaskResponseDto> findAll(Sort sort);

    TaskResponseDto find(Long id) throws TaskNotFoundException;

    TaskResponseDto create(TaskRequestDto TaskRequestDto);

    void delete(Long id) throws TaskNotFoundException;

    TaskResponseDto update(Long id, TaskRequestDto taskRequestDto) throws TaskNotFoundException;

    TaskResponseDto updateStatus(Long id, Long statusId) throws TaskNotFoundException, TaskStatusNotFoundException;

    TaskResponseDto updateAuthor(Long taskId, Long userId) throws TaskNotFoundException, UserNotFoundException;

    TaskResponseDto updatePriority(Long taskId, Long priorityId) throws TaskNotFoundException, TaskPriorityNotFoundException;

    TaskResponseDto updateAssignee(Long id, Long userId) throws TaskNotFoundException, UserNotFoundException;
}
