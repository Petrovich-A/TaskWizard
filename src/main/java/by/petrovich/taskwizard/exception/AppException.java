package by.petrovich.taskwizard.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final ErrorType errorType;
    private final Object[] params;

    public AppException(ErrorType errorType, Object... params) {
        super(String.format(errorType.getDescription(), params));
        this.errorType = errorType;
        this.params = params;
    }

}
