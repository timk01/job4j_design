create table author
(
    id          serial primary key,
    credentials varchar(128),
    wage        bigint,
    sex         char
);

create table books
(
    id        serial primary key,
    book_name varchar(64) unique,
    pages     int,
    price     real,
    FOREIGN KEY (author_id) int references author (id)
);

