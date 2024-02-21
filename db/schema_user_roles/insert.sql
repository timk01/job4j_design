/* fill tables with preliminary data */
INSERT INTO roles (name) VALUES ('Admin'), ('User');

INSERT INTO users (credentials, birthday, sex, email, password, role_id)
VALUES
  ('Ivanov Ivan Ivanovich', '1990-01-01', 'M', 'ivanov@example.com', 'wtf123@#$%@#$%', 1),
  ('Petrov Petr Petrovich', '1985-05-15', 'M', 'petrov@example.com', '1239simplepass', 2);

INSERT INTO rules (name) VALUES ('Rule1'), ('Rule2');

INSERT INTO roles_to_rules (role_id, rule_id) VALUES (1, 1), (1, 2), (2, 1);

INSERT INTO categories (name) VALUES ('Technical problems'), ('Feedback');

INSERT INTO states (name, explanation)
VALUES ('Pending', 'Specialists are working'), ('Finished', 'Your problem is solved, if its not open the new ticket');

INSERT INTO items (title, description, creation_time, user_id, category_id, state_id)
VALUES
  ('Fix the internet', 'Well, that shit isnt working, seriously ', CURRENT_TIMESTAMP, 1, 1, 1),
  ('Give rules to user Petrov Petr Perovich', 'Full one, please', CURRENT_TIMESTAMP, 2, 2, 2);

INSERT INTO comments (comment, creation_time, item_id)
VALUES ('No way i fix it by myself', CURRENT_TIMESTAMP, 1);

INSERT INTO attachs (name, explanation, creation_time, item_id) VALUES ('urgent.jpg', 'Seriosuly, faster', CURRENT_TIMESTAMP, 2);

