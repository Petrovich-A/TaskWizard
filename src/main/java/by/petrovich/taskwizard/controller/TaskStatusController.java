package by.petrovich.taskwizard.controller;

import by.petrovich.taskwizard.dto.request.TaskStatusRequestDto;
import by.petrovich.taskwizard.dto.response.TaskStatusResponseDto;
import by.petrovich.taskwizard.service.impl.TaskStatusServiceImpl;
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
@RequestMapping("/api/v1/status")
@RequiredArgsConstructor
@Validated
public class TaskStatusController {
    private final TaskStatusServiceImpl taskStatusService;

    @GetMapping("/statuses")
    public ResponseEntity<List<TaskStatusResponseDto>> findAll(@RequestParam(defaultValue = "ASC") String sortDirection,
                                                               @RequestParam(defaultValue = "id") String sortBy) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        return ResponseEntity.status(OK).body(taskStatusService.findAll(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskStatusResponseDto> find(@PathVariable @Positive Long id) {
        TaskStatusResponseDto taskStatusResponseDto = taskStatusService.find(id);
        return ResponseEntity.status(OK).body(taskStatusResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<TaskStatusResponseDto> create(@RequestBody @Valid TaskStatusRequestDto taskStatusRequestDto) {
        return ResponseEntity.status(CREATED).body(taskStatusService.create(taskStatusRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskStatusResponseDto> update(@PathVariable @Positive Long id,
                                                        @RequestBody @Valid TaskStatusRequestDto taskStatusRequestDto) {
        return ResponseEntity.status(OK).body(taskStatusService.update(id, taskStatusRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Positive Long id) {
        taskStatusService.delete(id);
        return ResponseEntity.status(OK).body("Task status deleted");
    }

}
