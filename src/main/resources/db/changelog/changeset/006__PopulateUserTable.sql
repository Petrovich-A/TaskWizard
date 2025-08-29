INSERT INTO users (id, email, password, name, created_at, updated_at)
VALUES (nextval('user_id_seq'), 'alice@example.com', '$2a$10$EkuhbdJ/2ooNQCgX4iY0oOfEPUkoP3Oo7KCo1EeHIf/9pPlrGkSOi', 'Alice', '2025-01-01 10:00:00',
        '2025-01-01 10:00:00'),
       (nextval('user_id_seq'), 'bob@example.com', '$2a$10$3k1qVrxLEtXAy7zuIa7vfOescjKmvNx//QQMV7ReFr3a46LQs3zaW', 'Bob', '2025-01-01 11:00:00',
        '2025-01-01 11:00:00'),
       (nextval('user_id_seq'), 'carol@example.com', '$2a$10$jT4tPsSJ8N6CqAQuWNS5YenRKvkJXpZAXNZNtUfm3DId.NQXKC0Ey', 'Carol', '2025-01-01 12:00:00',
        '2025-01-01 12:00:00'),
       (nextval('user_id_seq'), 'dave@example.com', '$2a$10$zAAlIl9I3POZXrommx2Qa.E6C5XjnssbGe5XwTOSVOfU4Obdizbz2', 'Dave', '2025-01-01 13:00:00',
        '2025-01-01 13:00:00'),
       (nextval('user_id_seq'), 'eve@example.com', '$2a$10$xNRXfa1K.w67gcYiCek0quqGVNli470Q.apOe3lb7xK1xOqwpwUk.', 'Eve', '2025-01-01 14:00:00',
        '2025-01-01 14:00:00');
