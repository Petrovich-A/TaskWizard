package by.petrovich.taskwizard.dto.request;

import by.petrovich.taskwizard.model.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserRequestDto {
    private Long id;

    private String email;

    private String password;

    private String name;

    private Set<Role> roles;

}
