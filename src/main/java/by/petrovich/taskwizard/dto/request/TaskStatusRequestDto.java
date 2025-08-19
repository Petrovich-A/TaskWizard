package by.petrovich.taskwizard.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskStatusRequestDto {
    private Long id;

    @NotBlank(message = "Name is required.")
    @Size(max = 30, message = "Name must not exceed 30 characters.")
    private String name;

}
