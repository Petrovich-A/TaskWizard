package by.petrovich.taskwizard.exception;

import lombok.Getter;

@Getter
public enum ErrorType {
    DEFAULT("An internal error occurred. Please try again later."),
    ENTITY_NOT_FOUND("The requested %s was not found."),
    ENTITY_NOT_FOUND_ON_UPDATE("The %s to update was not found."),
    DATA_INTEGRITY_VIOLATION("%s with the same unique property already exists."),
    ENTITY_ALREADY_EXISTS("The entity %s already exists."),
    ENTITY_CREATION_FAILED("Failed to create %s."),
    ENTITY_UPDATE_FAILED("Failed to update %s."),
    ENTITY_DELETION_FAILED("Failed to delete %s."),
    ENTITY_DELETION_FORBIDDEN("Deletion of %s is forbidden."),
    ASSIGNEE_NOT_FOUND("The task does not have an assigned user."),
    TASK_MODIFICATION_FORBIDDEN("You do not have permission to modify this task."),
    INVALID_INPUT("The provided data is invalid and does not meet the required criteria."),
    INVALID_TOKEN("The provided token is invalid."),
    MISSING_JWT_TOKEN("No token was provided in the request."),
    UNAUTHORIZED("Access is denied. Authorization is required.");

    private final String description;

    ErrorType(String description) {
        this.description = description;
    }
}
