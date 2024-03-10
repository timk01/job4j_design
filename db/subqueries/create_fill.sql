CREATE TABLE companies
(
    id   int primary key,
    city text
);

CREATE TABLE goods
(
    id         int primary key,
    name       text,
    company_id int references companies (id),
    price      int
);

CREATE TABLE sales_managers
(
    id          int primary key,
    last_name   text,
    first_name  text,
    company_id  int references companies (id),
    manager_fee int
);

CREATE TABLE managers
(
    id         int primary key,
    company_id int references companies (id)
);

INSERT INTO companies
VALUES (1, 'Москва'),
       (2, 'Нью-Йорк'),
       (3, 'Мюнхен');

INSERT INTO goods
VALUES (1, 'Небольшая квартира', 3, 5000),
       (2, 'Квартира в центре', 1, 4500),
       (3, 'Квартира у метро', 1, 3200),
       (4, 'Лофт', 2, 6700),
       (5, 'Загородный дом', 2, 9800);

INSERT INTO sales_managers
VALUES (1, 'Доу', 'Джон', 2, 2250),
       (2, 'Грубер', 'Ганс', 3, 3120),
       (3, 'Смит', 'Сара', 2, 1640),
       (4, 'Иванов', 'Иван', 1, 4500),
       (5, 'Купер', 'Грета', 3, 2130);

INSERT INTO managers
VALUES (1, 2),
       (2, 3),
       (4, 1);

SELECT AVG(manager_fee)
FROM sales_managers;
-- 2728 (solo query)

--query with subquery
-- вы хотите получить информацию только о тех менеджерах по продажах,
-- которые в прошлом месяце получили вознаграждение выше среднего (это также скалярный подзапрос)
-- 1. ровно ОДНА строка и ОДИН столбец // одно значение ((крч., оч. ограниченная выборка))
SELECT *
FROM sales_managers
WHERE manager_fee > (SELECT AVG(manager_fee) FROM sales_managers);

sELECT AVG(price)
FROM goods;
-- хотим, чтобы рядом с ценой товара отражалась средняя цена всех наших товаров
-- подзапрос полностью независим от запроса (см. пример выше)
SELECT name AS real_estate, price, (SELECT AVG(price) FROM goods) AS avg_price
FROM goods;

-- 2. Многострочный запрос
-- один столбец с несколькими строками ИЛИ
-- несколько столбцов с несколькими строками
-- часто WHERE + IN, NOT IN, ANY, ALL, EXISTS или NOT EXISTS

-- так, просто получение айдишников всех менеджеров
-- сначала происходит именно эта выборка
-- 1, 2, 4
SELECT managers.id
FROM managers;

-- так в среднем бы было 2728
SELECT AVG(manager_fee)
FROM sales_managers;

-- нам необходимо рассчитать среднее вознаграждение для менеджеров,
-- которые не внесены в таблицу managers (например, работают на фрилансе)
-- прмиер конечно несколько надуманный, т.к. я не увидел связи между менеджерами и сейлсами, но не суть.
-- 1640 (это 3) и 2130 (это 5), / 2 = 1885
SELECT AVG(manager_fee)
FROM sales_managers
WHERE sales_managers.id NOT IN (SELECT managers.id FROM managers);

-- 3. Коррелированные подзапросы.
-- Корреляция - зависимость.
-- в которых внутренний запрос опирается на информацию, полученную из внешнего запроса.
-- потому что получают часть инфы из запроса внешнего, ОТДЕЛЬНО НЕ РАБОТАЮТ.
-- коррелированные подзапросы обычно используются в операторах SELECT, WHERE и FROM

/*
-- отдельно он бы не работал (т.е. нужны ДЖОЙНЫ)
SELECT count(*)
FROM goods as g
WHERE c.id = g.company_id
 */

-- хотим подсчитать количество товаров, в каждой из наших компаний, то можно использовать следующий запрос
-- т.е. здесь мы тянем айдишник из компаний, сравниваем по нему (у нас же у товара тоже есть айди, а таблицы связаны)
-- и подсчитываем количество товаров как total goods
-- в выборке же просто обычный селект
SELECT city,
       (SELECT count(*)
        FROM goods as g
        WHERE c.id = g.company_id) as total_goods
FROM companies c;
/*
 Москва,2
Нью-Йорк,2
Мюнхен,1
 */

-- или проще, вероятно так (и работает быстрее чем сабкуери):
SELECT c.city, COUNT(g.name) AS total_goods
FROM companies c
         JOIN goods g ON c.id = g.company_id
GROUP BY c.city;

-- мы хотим получить информацию о тех менеджерах по продажам,
-- чье вознаграждение было равно или выше среднего вознаграждения для их компании
-- внутрений подзапрос вычисляет среднюю плату из тех же менеджеров, в которых компании совпадают

-- так выглядит выборка всех полей
/*
Доу,Джон,2,2250
Грубер,Ганс,3,3120
Смит,Сара,2,1640
Иванов,Иван,1,4500
Купер,Грета,3,2130
*/

-- а этот запрос заранее объединяет среднее по компаниям, т.е.
-- (2250+1640)/2=1945 у первой, 4500 у третьей, (3120+2130)/2=2625 у второй
SELECT last_name, first_name, manager_fee
FROM sales_managers sm1
WHERE sm1.manager_fee >= (SELECT AVG(manager_fee)
                          FROM sales_managers sm2
                          WHERE sm2.company_id = sm1.company_id);
-- опять же подзапрос не может существовать "соло"

--нам необходимо выбрать список id компаний средняя цена товаров в которой выше,
-- чем (тут внимание) половина максимальной цены среди цен всех товаров.
-- В данном случае мы будем использовать HAVING, также необходима группировка с помощью GROUP BY

SELECT company_id, AVG(price) AS average_price
FROM goods
GROUP BY company_id;
-- вообще аверага выглядит атк:
/*
 3,5000
2,8250
1,3850
 */

SELECT MAX(price)
FROM goods;
-- 9800 (4900 если половина)

SELECT company_id, AVG(price) AS average_price
FROM goods
GROUP BY company_id
HAVING AVG(price) > (SELECT MAX(price) FROM goods) / 2;
/*
 3,5000
2,8250
 */

-- В данном случае мы находим максимальную цену в подзапросе
-- и далее используем результат вычисления во внешнем запросе, который вернет в итоге только 2 строки.