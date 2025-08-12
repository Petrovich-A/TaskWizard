package by.petrovich.taskwizard.service;

import by.petrovich.taskwizard.dto.request.TaskRequestDto;
import by.petrovich.taskwizard.dto.response.TaskResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TaskService {
    Page<TaskResponseDto> findAll(Pageable pageable);

    List<TaskResponseDto> findAll(Sort sort);

    TaskResponseDto find(Long id);

    Page<TaskResponseDto> findByAuthor(Long authorId, Pageable pageable);

    Page<TaskResponseDto> findByAssignee(Long assigneeId, Pageable pageable);

    TaskResponseDto create(TaskRequestDto TaskRequestDto);

    void delete(Long id);

    TaskResponseDto update(Long id, TaskRequestDto taskRequestDto);

    TaskResponseDto updateStatus(Long id, Long statusId);

    TaskResponseDto updateAuthor(Long taskId, Long userId);

    TaskResponseDto updatePriority(Long taskId, Long priorityId);

    TaskResponseDto updateAssignee(Long id, Long userId);
}
