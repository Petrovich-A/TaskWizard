package by.petrovich.taskwizard.dto.request;

import lombok.Data;

@Data
public class SignUpRequestDto {
    private String userName;

    private String email;

    private String password;
}
