CREATE TABLE people
(
    id        SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    address   varchar(64)  NOT NULL,
    sex       varchar(16),
    UNIQUE (id, full_name)
);

CREATE TABLE movies
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE watched_movies
(
    person_id INT REFERENCES people (id),
    movie_id  INT REFERENCES movies (id),
    PRIMARY KEY (person_id, movie_id)
);

insert into people(full_name, address, sex)
values ('Ольга Егорова', '1-ый Казанский Переулок, д. 14', 'Женский'),
       ('Иванов Сергей', 'ул. Центральная, д. 40, кв. 74', 'Мужской'),
       ('Иванов Сергей', 'ул. Ленина, д. 7, кв. 130', 'Мужской');

insert into movies(title)
values ('Пираты Карибского Моря'),
       ('Матрица: революция'),
       ('Человек, который изменил все'),
       ('Интерстеллар');

-- это 3 форма.
-- 1 потому что а) строка целиком не повторяется, 2) атомарные столбцы (в каждой клеточке - 1 значение)
-- 2 потому что а) есть айдишник (он уникальный, т.к. ФИО - не уникальное),
-- б) есть зависимость полей только от ПК
-- 3 -- нет "лишних" не относящихся к табличке полей, не связанных с ПК напрямую

-- https://dbdiagram.io/home
-- pg_dump -h localhost -p 5432 -d university -U postgres -s -F p -E UTF-8 -f C:\projects\normzlization.sql
-- university - имя БД, Пострег - юзер (нужно убдет ввести пасс),
-- C:\projects\normzlization.sql - куда скидывать все безобразие

-- вставить import from postgres весь дамп
-- !!! ВАЖНО: Дамп может быть кривой и придется правтиь ручками.