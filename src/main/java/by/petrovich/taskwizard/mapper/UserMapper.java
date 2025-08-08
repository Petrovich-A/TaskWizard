package by.petrovich.taskwizard.mapper;

import by.petrovich.taskwizard.dto.request.UserRequestDto;
import by.petrovich.taskwizard.dto.response.UserResponseDto;
import by.petrovich.taskwizard.model.Task;
import by.petrovich.taskwizard.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "authoredTaskIds", source = "authoredTasks")
    @Mapping(target = "assignedTaskIds", source = "assignedTasks")
    UserResponseDto toResponseDto(User user);

    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "id", ignore = true)
    User toEntityUpdate(UserRequestDto userRequestDto, @MappingTarget User user);

    @Mapping(target = "id", source = "id")
    @Named("mapUser")
    User mapUser(Long id);

    default Task map(Long id) {
        if (id == null) {
            return null;
        }
        return Task.builder()
                .id(id)
                .build();
    }

    default Long map(Task task) {
        if (task == null) {
            return null;
        }
        return task.getId();
    }

    @Named("toAuthorName")
    default String toAuthorName(User user) {
        return user != null ? user.getName() : null;
    }

    @Named("toAssigneeName")
    default String toAssigneeName(User user) {
        return user != null ? user.getName() : null;
    }

}

