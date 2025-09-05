package by.petrovich.taskwizard.dto.request;

import by.petrovich.taskwizard.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserRequestDto {
    private Long id;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    @NotBlank(message = "Name is required.")
    @Size(max = 30, message = "Name must not exceed 30 characters.")
    private String name;

    private Set<Role> roles;

}
