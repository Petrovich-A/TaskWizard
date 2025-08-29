package by.petrovich.taskwizard.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCommentRequestDto {
    private Long id;

    @NotBlank(message = "Comment is required.")
    @Size(max = 200, message = "Comment must not exceed 200 characters.")
    private String comment;

    @NotNull(message = "Task ID is required.")
    private Long taskId;

    @NotNull(message = "Author ID is required.")
    private Long authorId;

}
