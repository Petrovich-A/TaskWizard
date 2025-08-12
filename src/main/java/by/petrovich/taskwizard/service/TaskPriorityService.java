package by.petrovich.taskwizard.service;

import by.petrovich.taskwizard.dto.request.TaskPriorityRequestDto;
import by.petrovich.taskwizard.dto.response.TaskPriorityResponseDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TaskPriorityService {

    List<TaskPriorityResponseDto> findAll(Sort sort);

    TaskPriorityResponseDto find(Long id);

    TaskPriorityResponseDto create(TaskPriorityRequestDto taskPriorityRequestDto);

    void delete(Long id);

    TaskPriorityResponseDto update(Long id, TaskPriorityRequestDto taskPriorityRequestDto);

    List<TaskPriorityResponseDto> find(String name);

}
