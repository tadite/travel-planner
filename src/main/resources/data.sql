INSERT INTO app_user VALUES (1, 'test1', 'pass1') ON CONFLICT (id) DO NOTHING;
INSERT INTO app_user VALUES (2, 'test2', 'pass2') ON CONFLICT (id) DO NOTHING;