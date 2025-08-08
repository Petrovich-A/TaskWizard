CREATE TABLE task_comment
(
    id         BIGSERIAL    NOT NULL,
    comment    VARCHAR(200) NOT NULL UNIQUE,
    created_at TIMESTAMP    NOT NULL,
    task_id    BIGINT       NOT NULL,
    PRIMARY KEY (id),

    CONSTRAINT fk_task_comment_task FOREIGN KEY (task_id) REFERENCES task (id)
);