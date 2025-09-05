package by.petrovich.taskwizard.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    @NotBlank(message = "Title is required.")
    @Size(max = 50, message = "Title must not exceed 50 characters.")
    private String title;

    @NotBlank(message = "Description is required.")
    @Size(max = 350, message = "Description must not exceed 350 characters.")
    private String description;

    @NotNull(message = "Task Status ID is required.")
    private Long taskStatusId;

    @NotNull(message = "Task Priority ID is required.")
    private Long taskPriorityId;

    @NotNull(message = "Author ID is required.")
    private Long authorId;

    private Long assigneeId;

}
