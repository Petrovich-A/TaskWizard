package by.petrovich.taskwizard.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private String type;

    private String title;

    private int status;

    private String detail;

    private String instance;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp;

    public static ErrorResponse build(String type, int status, String detail, String instance) {
        String typeFormatted = "/errors/" + type.toLowerCase();
        String titleFormatted = type.replace("_", " ").toLowerCase();
        return ErrorResponse.builder()
                .type(typeFormatted)
                .title(titleFormatted)
                .status(status)
                .detail(detail)
                .instance(instance)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
