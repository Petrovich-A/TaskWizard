INSERT INTO users (id, email, password, name, created_at, updated_at)
VALUES (nextval('user_id_seq'), 'alice@example.com', 'A1!s#9xPqZ', 'Alice', '2025-01-01 10:00:00',
        '2025-01-01 10:00:00'),
       (nextval('user_id_seq'), 'bob@example.com', 'B0b$Tr0ngP@ss', 'Bob', '2025-01-01 11:00:00',
        '2025-01-01 11:00:00'),
       (nextval('user_id_seq'), 'carol@example.com', 'C@r0l#2025!', 'Carol', '2025-01-01 12:00:00',
        '2025-01-01 12:00:00'),
       (nextval('user_id_seq'), 'dave@example.com', 'D4v3*Secure*Pwd', 'Dave', '2025-01-01 13:00:00',
        '2025-01-01 13:00:00'),
       (nextval('user_id_seq'), 'eve@example.com', 'Ev3!P@ssw0rd#', 'Eve', '2025-01-01 14:00:00',
        '2025-01-01 14:00:00');
