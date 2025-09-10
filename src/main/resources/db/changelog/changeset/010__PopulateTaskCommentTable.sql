INSERT INTO task_comment (id, comment, created_at, task_id, user_id)
VALUES (nextval('task_comment_id_seq'), 'Initial project setup looks good.', '2025-05-01T09:15:00', 1, 1),
       (nextval('task_comment_id_seq'), 'CI/CD pipeline configuration completed.', '2025-05-01T10:00:00', 1, 6),

       (nextval('task_comment_id_seq'), 'ER diagrams reviewed and approved.', '2025-05-02T11:00:00', 6, 6),

       (nextval('task_comment_id_seq'), 'Authentication module development started.', '2025-05-03T14:30:00', 11, 6),
       (nextval('task_comment_id_seq'), 'JWT integration is in progress.', '2025-05-03T15:00:00', 11, 1),
       (nextval('task_comment_id_seq'), 'Password encryption method chosen.', '2025-05-03T16:00:00', 11, 16),

       (nextval('task_comment_id_seq'), 'API documentation draft created.', '2025-05-04T11:30:00', 16, 6),

       (nextval('task_comment_id_seq'), 'Unit tests for payment module started.', '2025-05-06T14:00:00', 26, 1),
       (nextval('task_comment_id_seq'), 'Integration tests coverage at 70%.', '2025-05-06T15:00:00', 26, 11),

       (nextval('task_comment_id_seq'), 'Query optimization plan drafted.', '2025-05-07T10:30:00', 31, 21),

       (nextval('task_comment_id_seq'), 'Bug #123 fixed.', '2025-05-09T10:30:00', 41, 16),
       (nextval('task_comment_id_seq'), 'QA retesting scheduled.', '2025-05-09T11:00:00', 41, 6);