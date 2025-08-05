package by.petrovich.taskwizard.service;

import by.petrovich.taskwizard.dto.request.TaskPriorityRequestDto;
import by.petrovich.taskwizard.dto.response.TaskPriorityResponseDto;
import by.petrovich.taskwizard.exception.TaskNotFoundException;
import by.petrovich.taskwizard.exception.TaskPriorityNotFoundException;
import by.petrovich.taskwizard.exception.TaskStatusNotFoundException;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TaskPriorityService {

    List<TaskPriorityResponseDto> findAll(Sort sort);

    TaskPriorityResponseDto find(Long id) throws TaskStatusNotFoundException;

    TaskPriorityResponseDto create(TaskPriorityRequestDto taskPriorityRequestDto);

    void delete(Long id) throws TaskNotFoundException;

    TaskPriorityResponseDto update(Long id, TaskPriorityRequestDto taskPriorityRequestDto) throws TaskPriorityNotFoundException;

    List<TaskPriorityResponseDto> find(String name);

}
