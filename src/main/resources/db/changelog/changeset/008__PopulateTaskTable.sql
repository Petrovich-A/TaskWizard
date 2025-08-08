INSERT INTO task (id, title, description, created_at, updated_at, status_id, priority_id, author_id, assignee_id)
VALUES (nextval('task_id_seq'), 'Setup project',
        'Initialize the new project repository and configure basic CI/CD pipeline.',
        '2025-05-01 09:00:00', '2025-05-01 09:00:00', 1, 2, 1, 6),

       (nextval('task_id_seq'), 'Design database',
        'Create ER diagrams and define tables for the customer management system.',
        '2025-05-02 10:30:00', '2025-05-02 10:30:00', 2, 1, 1, NULL),

       (nextval('task_id_seq'), 'Implement login',
        'Develop user authentication with JWT and password encryption.',
        '2025-05-03 14:15:00', '2025-05-03 14:15:00', 2, 3, 6, 21),

       (nextval('task_id_seq'), 'API documentation',
        'Write comprehensive API documentation using Swagger.',
        '2025-05-04 11:00:00', '2025-05-04 11:00:00', 1, 1, 11, NULL),

       (nextval('task_id_seq'), 'Frontend prototype',
        'Build initial React components for the dashboard interface.',
        '2025-05-05 16:45:00', '2025-05-05 16:45:00', 1, 3, 16, 21),

       (nextval('task_id_seq'), 'Write tests',
        'Create unit and integration tests for the payment module.',
        '2025-05-06 13:20:00', '2025-05-06 13:20:00', 3, 2, 21, NULL),

       (nextval('task_id_seq'), 'Optimize queries',
        'Analyze and improve slow SQL queries for better performance.',
        '2025-05-07 09:50:00', '2025-05-07 09:50:00', 1, 2, 11, 11),

       (nextval('task_id_seq'), 'Deploy app',
        'Set up deployment scripts and deploy the app to staging environment.',
        '2025-05-08 15:10:00', '2025-05-08 15:10:00', 1, 3, 21, NULL),

       (nextval('task_id_seq'), 'Bug fixing',
        'Resolve reported issues from the last QA cycle.',
        '2025-05-09 10:05:00', '2025-05-09 10:05:00', 2, 2, 16, 1),

       (nextval('task_id_seq'), 'User feedback',
        'Collect and analyze user feedback to plan next sprint features.',
        '2025-05-10 14:30:00', '2025-05-10 14:30:00', 2, 1, 1, NULL);
