package by.petrovich.taskwizard.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskErrorResponse {
    private int status;
    private String error;
    private String message;
}
