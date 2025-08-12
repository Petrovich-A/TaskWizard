package by.petrovich.taskwizard.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final ErrorType error;
    private final Object[] params;

    public AppException(ErrorType error, Object... params) {
        super(String.format(error.getDescription(), params));
        this.error = error;
        this.params = params;
    }

}
