/* create tables, including needed ones for relations */
/*
В системе должны существовать:
Пользователи (users). Роли (roles). Права ролей (rules). Заявки (items).
Комментарии заявок (comments). Приложенные Файлы (attachs). Состояние заявки (states). Категории заявки (categories).
*/
/*
users - roles = many-to-one (у пользователя есть роли)
roles - rules = many-to-many (у ролей есть права)
items - users = many-to-one (у пользователя есть заявки)
items - comments = one-to-many (у заявки есть комментарии)
items - attachs = one-to-many (у заявки есть приложенные файлы)
items - categories = many-to-one (у заявки есть категории)
items - states = many-to-one (у заявки есть состояния)
Примечание. В скобочках написана хрень. Ориентироваться, судя по всему, на здравый смысл.
*/
create table roles(
    role_id serial primary key,
    name varchar(32)
);

create table users(
    user_id serial primary key,
    credentials varchar(64) not null,
    birthday DATE,
    sex char,
    email varchar (64) not null,
    password varchar (32) not null,
    role_id INT REFERENCES roles(role_id)
);

create table rules(
    rule_id serial primary key,
    name varchar(32)
);

create table roles_to_rules(
    role_id INT REFERENCES roles(role_id),
    rule_id INT REFERENCES rules(rule_id),
    PRIMARY KEY (role_id, rule_id)
);

CREATE TABLE categories (
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE states (
    state_id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    explanation text
);

CREATE TABLE items (
    item_id SERIAL PRIMARY KEY,
    title VARCHAR(128) NOT NULL,
    description TEXT,
    creation_time timestamp,
    user_id INT REFERENCES users(user_id),
    category_id INT REFERENCES categories(category_id),
    state_id INT REFERENCES states(state_id)
);

CREATE TABLE comments (
    comment_id SERIAL PRIMARY KEY,
    comment TEXT,
    creation_time TIMESTAMP,
    item_id int REFERENCES items(item_id)
);

CREATE TABLE attachs (
    attach_id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    explanation text,
    creation_time TIMESTAMP,
    item_id int REFERENCES items(item_id)
);


