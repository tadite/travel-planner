INSERT INTO app_user (id, username, password) VALUES (1, 'test1', 'pass1') ON CONFLICT (id) DO NOTHING;
INSERT INTO app_user (id, username, password) VALUES (2, 'test2', 'pass2') ON CONFLICT (id) DO NOTHING;
