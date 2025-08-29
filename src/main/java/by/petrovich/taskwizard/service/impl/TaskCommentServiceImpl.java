package by.petrovich.taskwizard.service.impl;

import by.petrovich.taskwizard.dto.request.TaskCommentRequestDto;
import by.petrovich.taskwizard.dto.response.TaskCommentResponseDto;
import by.petrovich.taskwizard.exception.AppException;
import by.petrovich.taskwizard.mapper.TaskCommentMapper;
import by.petrovich.taskwizard.model.TaskComment;
import by.petrovich.taskwizard.repository.TaskCommentRepository;
import by.petrovich.taskwizard.service.TaskCommentService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static by.petrovich.taskwizard.exception.ErrorType.DATA_INTEGRITY_VIOLATION;
import static by.petrovich.taskwizard.exception.ErrorType.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskCommentServiceImpl implements TaskCommentService {
    private final TaskCommentRepository taskCommentRepository;
    private final TaskCommentMapper commentMapper;
    private final EntityManager entityManager;

    @Override
    public List<TaskCommentResponseDto> findAll(Sort sort) {
        List<TaskComment> comments = taskCommentRepository.findAll(sort);
        return comments.stream()
                .map(commentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskCommentResponseDto find(Long id) {
        try {
            TaskComment taskComment = taskCommentRepository.findById(id)
                    .orElseThrow(() -> new AppException(ENTITY_NOT_FOUND, TaskComment.class.getSimpleName()));
            return commentMapper.toResponseDto(taskComment);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public TaskCommentResponseDto create(TaskCommentRequestDto taskCommentRequestDto) {
        try {
            TaskComment taskComment = commentMapper.toEntity(taskCommentRequestDto);
            TaskComment saved = taskCommentRepository.saveAndFlush(taskComment);
            entityManager.refresh(saved);
            return commentMapper.toResponseDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, TaskComment.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!taskCommentRepository.existsById(id)) {
            throw new AppException(ENTITY_NOT_FOUND, TaskComment.class.getSimpleName());
        }
        try {
            taskCommentRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(DATA_INTEGRITY_VIOLATION, TaskComment.class.getSimpleName());
        }
    }

}