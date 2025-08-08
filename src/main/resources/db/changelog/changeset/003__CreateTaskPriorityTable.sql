CREATE TABLE task_priority
(
    id        BIGSERIAL   NOT NULL,
    name      VARCHAR(30) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);