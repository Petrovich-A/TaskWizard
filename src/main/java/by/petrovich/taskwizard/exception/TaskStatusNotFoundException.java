package by.petrovich.taskwizard.exception;

public class TaskStatusNotFoundException extends RuntimeException {
    public TaskStatusNotFoundException(String message) {
        super(message);
    }
}
