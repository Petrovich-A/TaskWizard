package by.petrovich.taskwizard.mapper;

import by.petrovich.taskwizard.dto.request.TaskRequestDto;
import by.petrovich.taskwizard.dto.response.TaskResponseDto;
import by.petrovich.taskwizard.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TaskStatusMapper.class, TaskPriorityMapper.class, UserMapper.class})
public interface TaskMapper {
    @Mapping(target = "status", source = "taskStatus")
    @Mapping(target = "priority", source = "taskPriority")
    @Mapping(target = "author", source = "author", qualifiedByName = "toAuthorName")
    @Mapping(target = "assignee", source = "assignee", qualifiedByName = "toAssigneeName")
    TaskResponseDto toResponseDto(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "taskStatus", source = "taskStatusId", qualifiedByName = "mapStatus")
    @Mapping(target = "taskPriority", source = "taskPriorityId", qualifiedByName = "mapPriority")
    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapUser")
    @Mapping(target = "assignee", source = "assigneeId", qualifiedByName = "mapUser")
    Task toEntity(TaskRequestDto taskRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "taskStatus", ignore = true)
    @Mapping(target = "taskPriority", ignore = true)
    Task toEntityUpdate(TaskRequestDto taskRequestDto, @MappingTarget Task task);

}
