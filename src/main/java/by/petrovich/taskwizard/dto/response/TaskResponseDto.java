package by.petrovich.taskwizard.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaskResponseDto {
    private Long id;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String status;

    private String priority;

    private String author;

    private String assignee;

    private List<TaskCommentResponseDto> comments;
}
