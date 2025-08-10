package by.petrovich.taskwizard.service.impl;

import by.petrovich.taskwizard.dto.request.TaskCommentRequestDto;
import by.petrovich.taskwizard.dto.response.TaskCommentResponseDto;
import by.petrovich.taskwizard.exception.TaskCommentNotFoundException;
import by.petrovich.taskwizard.exception.UserNotFoundException;
import by.petrovich.taskwizard.mapper.TaskCommentMapper;
import by.petrovich.taskwizard.model.TaskComment;
import by.petrovich.taskwizard.repository.TaskCommentRepository;
import by.petrovich.taskwizard.service.TaskCommentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskCommentServiceImpl implements TaskCommentService {
    private final TaskCommentRepository taskCommentRepository;
    private final TaskCommentMapper commentMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TaskCommentResponseDto> findAll(Sort sort) {
        List<TaskComment> comments = taskCommentRepository.findAll(sort);
        return comments.stream()
                .map(commentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskCommentResponseDto find(Long id) throws TaskCommentNotFoundException {
        TaskComment taskComment = taskCommentRepository.findById(id)
                .orElseThrow(() -> new TaskCommentNotFoundException("Comment with id " + id + " not found"));
        return commentMapper.toResponseDto(taskComment);
    }

    @Override
    @Transactional
    public TaskCommentResponseDto create(TaskCommentRequestDto taskCommentRequestDto) {
        TaskComment taskComment = commentMapper.toEntity(taskCommentRequestDto);
        TaskComment saved = taskCommentRepository.saveAndFlush(taskComment);
        entityManager.refresh(saved);
        return commentMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) throws UserNotFoundException {
        if (taskCommentRepository.existsById(id)) {
            taskCommentRepository.deleteById(id);
        } else {
            throw new TaskCommentNotFoundException("Comment with id " + id + " not found");
        }
    }

}