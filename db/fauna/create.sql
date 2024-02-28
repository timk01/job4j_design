create table fauna
(
    id             serial primary key,
    name           text,
    avg_age        int,
    discovery_date date
);

create table scientists
(
    id serial primary key,
    name varchar(32),
    second_name varchar(48),
    speciality text,
    birthday date,
    fauna_id int references fauna(id) unique
);