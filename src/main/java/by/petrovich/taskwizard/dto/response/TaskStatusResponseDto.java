package by.petrovich.taskwizard.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static by.petrovich.taskwizard.constant.Constant.DATE_TIME_FORMAT_PATTERN;

@Data
@Builder
public class TaskStatusResponseDto {
    private Long id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_PATTERN)
    private LocalDateTime updatedAt;

}
