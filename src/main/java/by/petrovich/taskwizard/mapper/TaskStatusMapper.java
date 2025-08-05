package by.petrovich.taskwizard.mapper;

import by.petrovich.taskwizard.dto.request.TaskStatusRequestDto;
import by.petrovich.taskwizard.dto.response.TaskStatusResponseDto;
import by.petrovich.taskwizard.model.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.WARN)
public interface TaskStatusMapper {
    TaskStatusResponseDto toResponseDto(TaskStatus taskStatus);

    @Mapping(target = "id", ignore = true)
    TaskStatus toEntity(TaskStatusRequestDto taskStatusRequestDto);

    @Mapping(target = "id", ignore = true)
    TaskStatus toEntityUpdate(TaskStatusRequestDto taskStatusRequestDto, @MappingTarget TaskStatus taskStatus);

    @Mapping(target = "id", source = "id")
    @Named("mapStatus")
    TaskStatus map(Long id);

}
