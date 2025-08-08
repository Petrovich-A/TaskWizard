package by.petrovich.taskwizard.mapper;

import by.petrovich.taskwizard.dto.response.TaskCommentResponseDto;
import by.petrovich.taskwizard.model.TaskComment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.WARN)
public interface TaskCommentMapper {
    TaskCommentResponseDto toResponseDto(TaskComment taskComment);

}
