package by.petrovich.taskwizard.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

import static by.petrovich.taskwizard.constant.Constant.DATE_TIME_FORMAT_PATTERN;

@Data
@Builder
public class UserResponseDto {
    private Long id;

    private String email;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_PATTERN)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_PATTERN)
    private LocalDateTime updatedAt;

    private Set<Long> authoredTaskIds;

    private Set<Long> assignedTaskIds;
}
