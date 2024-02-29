CREATE TABLE departments
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE employees
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(64) NOT NULL,
    department_id INT REFERENCES departments (id)
);

-- Добавим департаменты
INSERT INTO departments (name)
VALUES ('HR Department'),
       ('Engineering Department'),
       ('Marketing Department');

-- Добавим сотрудников
INSERT INTO employees (name, department_id)
VALUES ('John Smith', 1),
       ('Alice Johnson', 1),
       ('Bob Davis', 1),
       ('Eva Williams', 2),
       ('Mike Brown', 2),
       ('Linda Jones', 2),
       ('Charlie Wilson', 3),
       ('Olivia Miller', 3),
       ('David Davis', 3),
       ('Sophia Taylor', 3),
       ('Daniel Harris', 3),
       ('Emma Moore', 3),
       ('William White', 3),
       ('Ava Robinson', 3),
       ('Mia Smith', 3);

INSERT INTO departments (name)
VALUES ('Empty souls'); -- Мервтые души. Для тестов.

INSERT INTO employees (name, department_id)
VALUES ('John Doe', null),
       ('John Doe Clone', null); -- работник, которого вроде бы и есть, но... нет

-- 5. Создать таблицу teens с атрибутами name, gender и заполнить ее.
CREATE TABLE teens
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR(32) NOT NULL,
    gender VARCHAR(10)  NOT NULL
);

INSERT INTO teens (name, gender)
VALUES ('Emma', 'Female'),
       ('Liam', 'Male'),
       ('Olivia', 'Female'),
       ('Noah', 'Male'),
       ('Ava', 'Female');