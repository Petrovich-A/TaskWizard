package by.petrovich.taskwizard.controller;

import by.petrovich.taskwizard.dto.request.UserRequestDto;
import by.petrovich.taskwizard.dto.response.UserResponseDto;
import by.petrovich.taskwizard.service.impl.UserServiceImpl;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> findAll(
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        List<UserResponseDto> users = userService.findAll(sort);
        return ResponseEntity.status(OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> find(@PathVariable @Positive Long id) {
        UserResponseDto userResponseDto = userService.find(id);
        return ResponseEntity.status(OK).body(userResponseDto);
    }

    @GetMapping("by-email/{email}")
    public ResponseEntity<UserResponseDto> find(@PathVariable String email) {
        UserResponseDto userResponseDto = userService.find(email);
        return ResponseEntity.status(OK).body(userResponseDto);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto createdUser = userService.create(userRequestDto);
        return ResponseEntity.status(CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable @Positive Long id,
                                                  @RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userService.update(id, userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Positive Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }
}
