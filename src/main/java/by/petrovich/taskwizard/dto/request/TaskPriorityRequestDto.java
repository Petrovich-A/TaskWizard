package by.petrovich.taskwizard.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskPriorityRequestDto {
    private Long id;

    private String name;

}
