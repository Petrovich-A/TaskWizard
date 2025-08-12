CREATE TABLE task_status
(
    id         BIGSERIAL   NOT NULL,
    name       VARCHAR(30) NOT NULL UNIQUE,
    updated_at TIMESTAMP   NOT NULL,
    PRIMARY KEY (id)
);