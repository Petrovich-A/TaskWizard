package by.petrovich.taskwizard.mapper;

import by.petrovich.taskwizard.dto.request.TaskCommentRequestDto;
import by.petrovich.taskwizard.dto.response.TaskCommentResponseDto;
import by.petrovich.taskwizard.model.Task;
import by.petrovich.taskwizard.model.TaskComment;
import by.petrovich.taskwizard.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.WARN)
public interface TaskCommentMapper {
    @Mapping(target = "author", source = "author.name")
    @Mapping(target = "taskId", source = "task.id")
    TaskCommentResponseDto toResponseDto(TaskComment taskComment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "task", source = "taskId")
    @Mapping(target = "author", source = "authorId")
    TaskComment toEntity(TaskCommentRequestDto taskCommentRequestDto);

    @Mapping(target = "id", source = "id")
    Task toTask(Long id);

    @Mapping(target = "id", source = "id")
    User toUser(Long id);

}
