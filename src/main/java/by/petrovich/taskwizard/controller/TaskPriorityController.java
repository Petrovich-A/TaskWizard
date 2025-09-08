package by.petrovich.taskwizard.controller;

import by.petrovich.taskwizard.dto.request.TaskPriorityRequestDto;
import by.petrovich.taskwizard.dto.response.TaskPriorityResponseDto;
import by.petrovich.taskwizard.service.TaskPriorityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/priority")
@RequiredArgsConstructor
@Validated
public class TaskPriorityController {
    private final TaskPriorityService taskPriorityService;

    @GetMapping("/priorities")
    public ResponseEntity<List<TaskPriorityResponseDto>> findAll(@RequestParam(defaultValue = "ASC") String sortDirection,
                                                                 @RequestParam(defaultValue = "id") String sortBy) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        return ResponseEntity.status(OK).body(taskPriorityService.findAll(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskPriorityResponseDto> find(@PathVariable @Positive Long id) {
        TaskPriorityResponseDto taskPriorityResponseDto = taskPriorityService.find(id);
        return ResponseEntity.status(OK).body(taskPriorityResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<TaskPriorityResponseDto> create(@RequestBody @Valid TaskPriorityRequestDto taskPriorityRequestDto) {
        return ResponseEntity.status(CREATED).body(taskPriorityService.create(taskPriorityRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskPriorityResponseDto> update(@PathVariable @Positive Long id,
                                                          @RequestBody @Valid TaskPriorityRequestDto taskPriorityRequestDto) {
        return ResponseEntity.status(OK).body(taskPriorityService.update(id, taskPriorityRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Positive Long id) {
        taskPriorityService.delete(id);
        return ResponseEntity.status(OK).body("Task priority deleted");
    }
}

