CREATE TABLE users
(
    id         BIGINT      NOT NULL,
    email      VARCHAR(50) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL UNIQUE,
    name       VARCHAR(30) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP   NOT NULL,
    PRIMARY KEY (id)
);

CREATE SEQUENCE user_id_seq START 1
    INCREMENT BY 5
    MINVALUE 1;
