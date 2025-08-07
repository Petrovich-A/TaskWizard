package by.petrovich.taskwizard.mapper;

import by.petrovich.taskwizard.dto.request.TaskPriorityRequestDto;
import by.petrovich.taskwizard.dto.response.TaskPriorityResponseDto;
import by.petrovich.taskwizard.model.TaskPriority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.WARN)
public interface TaskPriorityMapper {
    TaskPriorityResponseDto toResponseDto(TaskPriority taskPriority);

    @Mapping(target = "id", ignore = true)
    TaskPriority toEntity(TaskPriorityRequestDto taskPriorityRequestDto);

    @Mapping(target = "id", ignore = true)
    TaskPriority toEntityUpdate(TaskPriorityRequestDto taskPriorityRequestDto, @MappingTarget TaskPriority taskPriority);

    @Mapping(target = "id", source = "id")
    @Named("mapPriority")
    TaskPriority mapPriority(Long id);

    default String toName(TaskPriority taskPriority) {
        return taskPriority != null ? taskPriority.getName() : null;
    }

}
