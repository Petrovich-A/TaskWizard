package by.petrovich.taskwizard.service;

import by.petrovich.taskwizard.dto.request.TaskCommentRequestDto;
import by.petrovich.taskwizard.dto.response.TaskCommentResponseDto;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskCommentService {
    List<TaskCommentResponseDto> findAll(Sort sort);

    TaskCommentResponseDto find(Long id);

    @Transactional
    TaskCommentResponseDto create(TaskCommentRequestDto taskCommentRequestDto);

    @Transactional
    void delete(Long id);
}
