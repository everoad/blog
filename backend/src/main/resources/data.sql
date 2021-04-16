INSERT INTO member(member_id, name, password, use_yn, created_time, modified_time) VALUES ('tester', 'tester', '{bcrypt}$2a$10$ukcp2fpbH9hBGg3/HxxpxeKD6CsLRCXUbHTza6fJxwKoypE6JjfPy', true, now(), now());

INSERT INTO member_role(member_id, role) VALUES ('tester', 'ADMIN');