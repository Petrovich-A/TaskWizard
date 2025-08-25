package by.petrovich.taskwizard.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequestDto {
    @NotBlank(message = "The username must not be blank")
    @Size(min = 3, max = 30, message = "The username '${validatedValue}' must be between {min} and {max} characters long")
    private String userName;

    @Email(message = "The email '${validatedValue}' must be a valid email address")
    @NotBlank(message = "The email must not be blank")
    @Size(max = 50, message = "The email '${validatedValue}' must be at most {max} characters long")
    private String email;

    @NotBlank(message = "The password must not be blank")
    @Size(min = 8, max = 255, message = "The password must be between {min} and {max} characters long")
    private String password;
}
