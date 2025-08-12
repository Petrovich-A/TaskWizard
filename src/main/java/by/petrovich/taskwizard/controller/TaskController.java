package by.petrovich.taskwizard.controller;

import by.petrovich.taskwizard.dto.request.TaskRequestDto;
import by.petrovich.taskwizard.dto.response.TaskResponseDto;
import by.petrovich.taskwizard.service.impl.TaskServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@Validated
public class TaskController {
    private final TaskServiceImpl taskService;

    @GetMapping("/tasks")
    public ResponseEntity<Page<TaskResponseDto>> findAll(@RequestParam(name = "page", defaultValue = "0") @Min(0) int page,
                                                         @RequestParam(name = "size", defaultValue = "3") @Min(0) int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(OK).body(taskService.findAll(pageable));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<TaskResponseDto>> findAll(@RequestParam(defaultValue = "ASC") String sortDirection,
                                                         @RequestParam(defaultValue = "id") String sortBy) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        return ResponseEntity.status(OK).body(taskService.findAll(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> find(@PathVariable @Positive Long id) {
        TaskResponseDto TaskResponseDto = taskService.find(id);
        return ResponseEntity.status(OK).body(TaskResponseDto);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<TaskResponseDto>> findByAuthor(@PathVariable @Positive Long authorId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(OK).body(taskService.findByAuthor(authorId, pageable));
    }

    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<Page<TaskResponseDto>> findByAssignee(@PathVariable @Positive Long assigneeId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(OK).body(taskService.findByAssignee(assigneeId, pageable));
    }

    @PostMapping("/")
    public ResponseEntity<TaskResponseDto> create(@RequestBody @Valid TaskRequestDto taskRequestDto) {
        return ResponseEntity.status(CREATED).body(taskService.create(taskRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> update(@PathVariable @Positive Long id,
                                                  @RequestBody @Valid TaskRequestDto TaskRequestDto) {
        return ResponseEntity.status(OK).body(taskService.update(id, TaskRequestDto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDto> updateStatus(@PathVariable @Positive Long id, @RequestParam Long statusId) {
        return ResponseEntity.status(OK).body(taskService.updateStatus(id, statusId));
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<TaskResponseDto> updatePriority(@PathVariable @Positive Long id, @RequestParam Long priorityId) {
        return ResponseEntity.status(OK).body(taskService.updatePriority(id, priorityId));
    }

    @PatchMapping("/{id}/author")
    public ResponseEntity<TaskResponseDto> updateAuthor(@PathVariable @Positive Long id, @RequestParam Long authorId) {
        return ResponseEntity.status(OK).body(taskService.updateAuthor(id, authorId));
    }

    @PatchMapping("/{id}/assignee")
    public ResponseEntity<TaskResponseDto> updateAssignee(@PathVariable @Positive Long id, @RequestParam Long assigneeId) {
        return ResponseEntity.status(OK).body(taskService.updateAssignee(id, assigneeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Positive Long id) {
        taskService.delete(id);
        return ResponseEntity.status(NO_CONTENT).body("Task deleted");
    }

}
