package by.petrovich.taskwizard.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCommentRequestDto {
    private Long id;

    private String comment;

    private Long taskId;

    private Long authorId;

}
