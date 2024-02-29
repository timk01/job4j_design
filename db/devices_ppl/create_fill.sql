create table devices
(
    id    serial primary key,
    name  varchar(255),
    price float
);

create table people
(
    id   serial primary key,
    name varchar(255)
);

create table devices_people
(
    id        serial primary key,
    device_id int references devices (id),
    people_id int references people (id)
);

-- Заполнение таблицы устройств
INSERT INTO devices (name, price)
VALUES ('Laptop', 1000),
       ('Tablet', 500),
       ('Smartphone', 800),
       ('Smartwatch', 300),
       ('Camera', 700),
       ('Headphones', 200);

-- Заполнение таблицы пользователей
INSERT INTO people (name)
VALUES ('John Smith'),
       ('Emma Johnson'),
       ('Michael Davis'),
       ('Sophia Wilson'),
       ('Daniel Brown'),
       ('Emily Taylor'),
       ('William Miller'),
       ('Olivia Jones'),
       ('Benjamin Moore'),
       ('Ava Robinson');

-- Заполнение таблицы устройств пользователей
INSERT INTO devices_people (device_id, people_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 3),
       (5, 4),
       (6, 5),
       (1, 6),
       (3, 6),
       (2, 7),
       (4, 8),
       (5, 9),
       (6, 10);

select *
from devices;

-- вывести просто человека и все устройства, что у него есть
select p.name,
       array_agg(d.name)
from people as p
         inner join devices_people dp on p.id = dp.people_id
         inner join devices d on dp.device_id = d.id
group by p.name;
-- после 1 прохода
/*
 Emma Johnson,{Smartphone}
Sophia Wilson,{Camera}
Olivia Jones,{Smartwatch}
Benjamin Moore,{Camera}
John Smith,"{Laptop,Tablet}"
Michael Davis,{Smartwatch}
Emily Taylor,"{Laptop,Smartphone}"
Daniel Brown,{Headphones}
William Miller,{Tablet}
Ava Robinson,{Headphones}
 */

INSERT INTO devices_people (device_id, people_id)
VALUES (4, 1),
       (5, 1),
       (6, 2),
       (1, 3),
       (2, 3),
       (3, 4),
       (4, 4),
       (5, 5),
       (6, 5),
       (1, 6),
       (2, 6),
       (3, 7),
       (4, 7),
       (5, 7),
       (6, 7),
       (1, 8),
       (2, 8),
       (3, 9),
       (4, 9),
       (5, 10),
       (6, 10);

select p.name,
       array_agg(d.name)
from people as p
         inner join devices_people dp on p.id = dp.people_id
         inner join devices d on dp.device_id = d.id
group by p.name;
-- после добавления данных
/*
Emma Johnson,"{Smartphone,Headphones}"
Sophia Wilson,"{Camera,Smartphone,Smartwatch}"
Olivia Jones,"{Smartwatch,Laptop,Tablet}"
Benjamin Moore,"{Camera,Smartphone,Smartwatch}"
John Smith,"{Laptop,Tablet,Smartwatch,Camera}"
Michael Davis,"{Smartwatch,Laptop,Tablet}"
Emily Taylor,"{Laptop,Smartphone,Laptop,Tablet}"
Daniel Brown,"{Headphones,Camera,Headphones}"
William Miller,"{Tablet,Smartphone,Smartwatch,Camera,Headphones}"
Ava Robinson,"{Headphones,Camera,Headphones}"
 */

-- посчитать сколько всего устройств у человека
select p.name,
       count(d.price) as count
from devices as d
         join devices_people as dp on d.id = dp.device_id
         join people as p on dp.people_id = p.id
group by p.name;
-- после добавления "дорогоих устройтв" у Эмилии и Вильяма их по 5
/*
 Emma Johnson,2
Sophia Wilson,3
Olivia Jones,3
Benjamin Moore,3
John Smith,5
Michael Davis,3
Emily Taylor,5
Daniel Brown,4
William Miller,5
Ava Robinson,4
 */

 -- Добавление дорогих устройств для нескольких пользователей
 INSERT INTO devices (name, price)
 VALUES ('High-end Laptop', 7000),
        ('Professional Camera', 6000),
        ('Gaming Console', 5500),
        ('Premium Smartphone', 6500);

 -- Распределение дорогих устройств по пользователям
 INSERT INTO devices_people (device_id, people_id)
 VALUES (7, 1), -- John Smith - High-end Laptop
        (8, 5), -- Daniel Brown - Professional Camera
        (9, 6), -- Emily Taylor - Gaming Console
        (10, 10); -- Ava Robinson - Premium Smartphone

