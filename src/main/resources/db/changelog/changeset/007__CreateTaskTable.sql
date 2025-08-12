CREATE TABLE task
(
    id          BIGINT       NOT NULL,
    title       VARCHAR(50)  NOT NULL UNIQUE,
    description VARCHAR(350) NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL,
    status_id   BIGINT       NOT NULL,
    priority_id BIGINT       NOT NULL,
    author_id   BIGINT       NOT NULL,
    assignee_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_task_status FOREIGN KEY (status_id) REFERENCES task_status (id),
    CONSTRAINT fk_task_priority FOREIGN KEY (priority_id) REFERENCES task_priority (id),
    CONSTRAINT fk_task_author FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT fk_task_assignee FOREIGN KEY (assignee_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE SEQUENCE task_id_seq START 1
    INCREMENT BY 5
    MINVALUE 1;
