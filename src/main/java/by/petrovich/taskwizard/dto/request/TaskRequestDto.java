package by.petrovich.taskwizard.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskRequestDto {
    private String title;

    private String description;

    private Long taskStatusId;

    private Long taskPriorityId;

    private Long authorId;

    private Long assigneeId;

}
