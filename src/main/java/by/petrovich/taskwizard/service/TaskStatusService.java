package by.petrovich.taskwizard.service;

import by.petrovich.taskwizard.dto.request.TaskStatusRequestDto;
import by.petrovich.taskwizard.dto.response.TaskStatusResponseDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TaskStatusService {

    List<TaskStatusResponseDto> findAll(Sort sort);

    TaskStatusResponseDto find(Long id);

    TaskStatusResponseDto create(TaskStatusRequestDto taskStatusRequestDto);

    void delete(Long id);

    TaskStatusResponseDto update(Long id, TaskStatusRequestDto taskStatusRequestDto);

    List<TaskStatusResponseDto> find(String name);

}
