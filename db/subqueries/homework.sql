CREATE TABLE customers
(
    id         serial primary key,
    first_name text,
    last_name  text,
    age        int,
    country    text
);

-- Добавьте в таблицу несколько записей.
-- Выполните запрос, который вернет список клиентов, возраст которых является минимальным.
INSERT INTO customers (first_name, last_name, age, country)
VALUES ('John', 'Doe', 25, 'USA'),
       ('Alice', 'Smith', 30, 'Canada'),
       ('Bob', 'Johnson', 22, 'UK'),
       ('Emma', 'Lee', 28, 'Australia'),
       ('Michael', 'Wang', 35, 'China'),
       ('Sophia', 'Kim', 29, 'South Korea'),
       ('Daniel', 'Garcia', 31, 'Spain'),
       ('Olivia', 'Müller', 26, 'Germany'),
       ('Liam', 'Rossi', 27, 'Italy'),
       ('Mia', 'Nakamura', 33, 'Japan');

INSERT INTO customers (first_name, last_name, age, country)
VALUES ('Sophie', 'Johnson', 22, 'USA'),
       ('William', 'Clark', 22, 'Canada'),
       ('Grace', 'Li', 27, 'China');

select c.first_name, c.last_name, c.age
from customers as c
where c.age = (select min(age) from customers);
/*
 Bob,Johnson,22
Sophie,Johnson,22
William,Clark,22
 */

--2. К уже представленной таблице customers добавляем следующую таблицу:

CREATE TABLE orders
(
    id          serial primary key,
    amount      int,
    customer_id int references customers (id)
);

/*
Добавьте в таблицу несколько записей.
Необходимо выполнить запрос, который вернет список пользователей, которые еще не выполнили ни одного заказа.
Используйте подзапрос, для реализации Вам понадобится NOT IN.
 */

INSERT INTO orders (amount, customer_id)
VALUES (100, 1),
       (150, 2),
       (200, 3),
       (120, 4),
       (180, 6),
       (90, 7),
       (250, 9),
       (130, 10);

-- вернет просто всех покупателей.
select c.id, c.first_name, c.last_name, c.age
from customers as c;

-- это аналог NOT IN
-- не будут отражаться 1 и 2
-- а нам нужно 5, 8, 11, 12, 13 по идее без заказов
SELECT c.id, c.first_name, c.last_name, c.age
FROM customers AS c
WHERE c.id != 1
  and c.id != 2;

-- это ордерс (1, 2, 3, 4, 6... - с заказами)
/*
 1,100,1
2,150,2
3,200,3
4,120,4
5,180,6
6,90,7
7,250,9
8,130,10
 */

-- если пользователь не сделал НИ ОДНОГО заказа, значит его, пользователя айдишника, не будет в ордерах.
-- т.е. нам нужно из таблицы ЗАКАЗОВ вычленить все те айдишники, причем покупателя (!!!), которые там есть
-- это азказы, что уже сделаны (ссылаются на покупателя).
-- соовтетственно тех заказов, что мы не видим в ордерах - у тех нет закзачика.
select c.id, c.first_name, c.last_name, c.age
from customers as c
where c.id not in (select o.customer_id from orders as o);

/*
5,Michael,Wang,35
8,Olivia,Müller,26
11,Sophie,Johnson,22
12,William,Clark,22
13,Grace,Li,27
*/

-- обычный лефтджойн, джойнит (аутер) другую таблицу к нашей (в отличие от простого джойна, где нунжы пересечения)
select c.id, c.first_name, c.last_name, c.age, o2.amount
from customers as c
         left join orders o2 on c.id = o2.customer_id;

-- обрати внимание что у последних 5 эмаунт = 0
/*
 1,John,Doe,25,100
2,Alice,Smith,30,150
3,Bob,Johnson,22,200
4,Emma,Lee,28,120
6,Sophia,Kim,29,180
7,Daniel,Garcia,31,90
9,Liam,Rossi,27,250
10,Mia,Nakamura,33,130
11,Sophie,Johnson,22,
12,William,Clark,22,
13,Grace,Li,27,
5,Michael,Wang,35,
8,Olivia,Müller,26,
 */

select c.id, c.first_name, c.last_name, c.age
from customers as c
         left join orders o2 on c.id = o2.customer_id
where o2.id is null;
-- тот же результат, что и у нашего запроса с сабкуэри

-- тогда как если бы была задача например сделать выборку всех тех, у кого ЕСТЬ заказ, можно использовать так
-- т.к. иннер джойн = полное пересечение двух таблиц
SELECT c.id, c.first_name, c.last_name, c.age, o.amount
FROM customers c
         INNER JOIN orders o ON c.id = o.customer_id;
