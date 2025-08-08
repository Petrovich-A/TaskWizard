package by.petrovich.taskwizard.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {
    private Long id;

    private String email;

    private String password;

    private String name;

}
