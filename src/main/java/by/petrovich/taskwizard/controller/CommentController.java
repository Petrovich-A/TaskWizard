package by.petrovich.taskwizard.controller;

import by.petrovich.taskwizard.dto.request.TaskCommentRequestDto;
import by.petrovich.taskwizard.dto.response.TaskCommentResponseDto;
import by.petrovich.taskwizard.service.impl.TaskCommentServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final TaskCommentServiceImpl commentService;

    @GetMapping("/comments")
    public ResponseEntity<List<TaskCommentResponseDto>> findAll(@RequestParam(defaultValue = "ASC") String sortDirection,
                                                                @RequestParam(defaultValue = "id") String sortBy) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        List<TaskCommentResponseDto> comments = commentService.findAll(sort);
        return ResponseEntity.status(OK).body(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskCommentResponseDto> find(@PathVariable @Positive Long id) {
        TaskCommentResponseDto taskCommentResponseDto = commentService.find(id);
        return ResponseEntity.status(OK).body(taskCommentResponseDto);
    }

    @PostMapping
    public ResponseEntity<TaskCommentResponseDto> create(@RequestBody @Valid TaskCommentRequestDto taskCommentRequestDto) {
        TaskCommentResponseDto taskCommentResponseDto = commentService.create(taskCommentRequestDto);
        return ResponseEntity.status(CREATED).body(taskCommentResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Positive Long id) {
        commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Comment deleted");
    }
}
