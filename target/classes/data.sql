INSERT INTO realq.roles (id, description, name) VALUES (1, 'SYSTEM ADMINISTRATION', 'ROLE_ADMIN');
INSERT INTO realq.roles (id, description, name) VALUES (2, 'SYSTEM MANAGEMENT', 'ROLE_MANAGER');
INSERT INTO realq.roles (id, description, name) VALUES (3, 'SYSTEM VIEWER', 'ROLE_GUEST');

INSERT INTO realq.workers (id, created_at, deleted_at, updated_at, login, name, password, role_id, task_id) VALUES (1, '2019-03-29 18:00:10', null, '2019-03-29 18:02:43', 'admin', 'Admin', '$2a$10$RSfTNQi/ZTp03xG0i4i/s.j.655/1U18De9BPFZRndLbLEiRcJW0K', 1, null);
INSERT INTO realq.workers (id, created_at, deleted_at, updated_at, login, name, password, role_id, task_id) VALUES (2, '2019-03-29 18:00:10', null, '2019-03-29 18:02:43', 'viewer', 'Viewer2', '$2a$10$RSfTNQi/ZTp03xG0i4i/s.j.655/1U18De9BPFZRndLbLEiRcJW0K', 3, null);