package by.petrovich.taskwizard.dto.response;

import by.petrovich.taskwizard.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class JwtAuthenticationResponseDto {
    private Long userId;

    private String accessToken;

    private String email;

    private String name;

    private Set<Role> roles;

}
