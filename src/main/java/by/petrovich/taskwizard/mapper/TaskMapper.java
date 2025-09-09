package by.petrovich.taskwizard.mapper;

import by.petrovich.taskwizard.dto.request.TaskRequestDto;
import by.petrovich.taskwizard.dto.response.TaskResponseDto;
import by.petrovich.taskwizard.exception.AppException;
import by.petrovich.taskwizard.model.Task;
import by.petrovich.taskwizard.model.TaskPriority;
import by.petrovich.taskwizard.model.TaskStatus;
import by.petrovich.taskwizard.model.User;
import by.petrovich.taskwizard.repository.TaskPriorityRepository;
import by.petrovich.taskwizard.repository.TaskStatusRepository;
import by.petrovich.taskwizard.repository.UserRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import static by.petrovich.taskwizard.exception.ErrorType.ENTITY_NOT_FOUND;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TaskStatusMapper.class, TaskPriorityMapper.class,
        UserMapper.class, TaskCommentMapper.class})
public interface TaskMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "taskComments", ignore = true)
    @Mapping(target = "taskStatus", source = "taskStatusId", qualifiedByName = "mapStatus")
    @Mapping(target = "taskPriority", source = "taskPriorityId", qualifiedByName = "mapPriority")
    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapUser")
    @Mapping(target = "assignee", source = "assigneeId", qualifiedByName = "mapUser")
    Task toEntity(TaskRequestDto taskRequestDto, @Context TaskStatusRepository taskStatusRepository,
                  @Context TaskPriorityRepository taskPriorityRepository,
                  @Context UserRepository userRepository);

    @Mapping(target = "status", source = "taskStatus")
    @Mapping(target = "priority", source = "taskPriority")
    @Mapping(target = "author", source = "author", qualifiedByName = "toAuthorName")
    @Mapping(target = "assignee", source = "assignee", qualifiedByName = "toAssigneeName")
    @Mapping(target = "comments", source = "taskComments")
    TaskResponseDto toResponseDto(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "taskComments", ignore = true)
    @Mapping(target = "taskStatus", source = "taskStatusId", qualifiedByName = "mapStatus")
    @Mapping(target = "taskPriority", source = "taskPriorityId", qualifiedByName = "mapPriority")
    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapUserTaskMapper")
    @Mapping(target = "assignee", source = "assigneeId", qualifiedByName = "mapUserTaskMapper")
    Task toEntityUpdate(TaskRequestDto taskRequestDto, @MappingTarget Task task,
                        @Context TaskStatusRepository taskStatusRepository,
                        @Context TaskPriorityRepository taskPriorityRepository,
                        @Context UserRepository userRepository);

    @Named("mapStatus")
    default TaskStatus mapStatus(Long id, @Context TaskStatusRepository taskStatusRepository) {
        if (id == null) return null;
        return taskStatusRepository.findById(id)
                .orElseThrow(() -> new AppException(ENTITY_NOT_FOUND, TaskStatus.class));
    }

    @Named("mapPriority")
    default TaskPriority mapPriority(Long id, @Context TaskPriorityRepository taskPriorityRepository) {
        if (id == null) return null;
        return taskPriorityRepository.findById(id)
                .orElseThrow(() -> new AppException(ENTITY_NOT_FOUND, TaskPriority.class.getSimpleName()));
    }

    @Named("mapUserTaskMapper")
    default User mapUser(Long id, @Context UserRepository userRepository) {
        if (id == null) return null;
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ENTITY_NOT_FOUND, User.class.getSimpleName()));
    }

}
