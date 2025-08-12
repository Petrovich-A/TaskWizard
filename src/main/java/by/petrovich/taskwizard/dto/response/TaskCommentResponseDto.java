package by.petrovich.taskwizard.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskCommentResponseDto {
    private Long id;

    private String comment;

    private LocalDateTime createdAt;

    private String author;

    private Long taskId;

}
