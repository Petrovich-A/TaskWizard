package by.petrovich.taskwizard.service;

import by.petrovich.taskwizard.dto.request.TaskStatusRequestDto;
import by.petrovich.taskwizard.dto.response.TaskStatusResponseDto;
import by.petrovich.taskwizard.exception.TaskNotFoundException;
import by.petrovich.taskwizard.exception.TaskStatusNotFoundException;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TaskStatusService {

    List<TaskStatusResponseDto> findAll(Sort sort);

    TaskStatusResponseDto find(Long id) throws TaskStatusNotFoundException;

    TaskStatusResponseDto create(TaskStatusRequestDto taskStatusRequestDto);

    void delete(Long id) throws TaskNotFoundException;

    TaskStatusResponseDto update(Long id, TaskStatusRequestDto taskStatusRequestDto) throws TaskStatusNotFoundException;

    List<TaskStatusResponseDto> find(String name);

}
