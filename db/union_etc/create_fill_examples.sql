CREATE TABLE customer
(
    first_name text,
    last_name  text
);

CREATE TABLE employee
(
    first_name text,
    last_name  text
);

-- к слову, таблицы не связаны

INSERT INTO customer
VALUES ('Иван', 'Иванов'),
       ('Петр', 'Сергеев'),
       ('Ирина', 'Бросова'),
       ('Анна', 'Опушкина'),
       ('Потап', 'Потапов');

INSERT INTO employee
VALUES ('Кристина', 'Позова'),
       ('Михаил', 'Кругов'),
       ('Анна', 'Опушкина'),
       ('Иван', 'Иванов'),
       ('Сергей', 'Петров');

-- по смыслу, очень похоже на outer join (тюею 2 кружка + нечто между ними, молное пересечение)
-- но разница в том, что сли при юнионе инфа в столбик, то при джойне это именно присоединение

SELECT first_name, last_name
FROM customer
UNION
SELECT first_name, last_name
FROM employee;

-- при этом повторяющийся столбец 'Анна', 'Опушкина', Иван,Иванов, будет 1 раз без дублей:
/*
 Анна,Опушкина
Потап,Потапов
Кристина,Позова
Сергей,Петров
Иван,Иванов
Ирина,Бросова
Михаил,Кругов
Петр,Сергеев
 */

-- можно и с сортировкой, но она в конец последнего юниона
SELECT first_name, last_name
FROM customer
UNION
SELECT first_name, last_name
FROM employee
ORDER BY first_name, last_name;

/*
-- можно даже так, т.е. делать выборку посредством сокращения
SELECT first_name, last_name
FROM customer
WHERE status = 'Active'
UNION
SELECT first_name, last_name
FROM employee
WHERE emp_status = 'Current'
ORDER BY first_name, last_name;
 */

-- так БЕЗ нулей. 2 повтора по пересечениям двух таблиц
SELECT e.first_name,
       e.last_name,
       c.first_name,
       c.last_name
FROM employee e
         INNER JOIN customer c
                    ON e.first_name = c.first_name
                        AND e.last_name = c.last_name;
/*
 Анна,Опушкина,Анна,Опушкина
Иван,Иванов,Иван,Иванов
 */

-- так будут нули, т.е. вначале 2 строки с пересечением как при иннер джойне,
-- а потом остальные строки, в которым пересечений нет
-- итого, значимых значений 8+2, или 10; на 8 строках
SELECT e.first_name,
       e.last_name,
       c.first_name,
       c.last_name
FROM employee e
         full outer JOIN customer c
                         ON e.first_name = c.first_name
                             AND e.last_name = c.last_name;

-- так, если убрать пересечение вовсе
SELECT e.first_name,
       e.last_name,
       c.first_name,
       c.last_name
FROM employee e
         FULL OUTER JOIN customer c
                         ON e.first_name = c.first_name
                             AND e.last_name = c.last_name
WHERE e.first_name IS NULL
   OR c.first_name IS NULL
   OR e.last_name IS NULL
   OR c.last_name IS NULL;

-- так, напомню, анна и иван только по 1 разу.
SELECT first_name, last_name
FROM customer
UNION
SELECT first_name, last_name
FROM employee;


-- так будет примерно нечто похожее (хотя и извращение, да) чистого юниона.
SELECT e.first_name AS employee_first_name,
       e.last_name  AS employee_last_name,
       c.first_name AS customer_first_name,
       c.last_name  AS customer_last_name
FROM employee e
         LEFT JOIN customer c
                   ON e.first_name = c.first_name
                       AND e.last_name = c.last_name

UNION

SELECT e.first_name AS employee_first_name,
       e.last_name  AS employee_last_name,
       c.first_name AS customer_first_name,
       c.last_name  AS customer_last_name
FROM employee e
         RIGHT JOIN customer c
                    ON e.first_name = c.first_name
                        AND e.last_name = c.last_name
WHERE e.first_name IS NULL;

-- здесь будут дубликаты, т.к. в отличии от юниона тут нет "зашитого" disctinct (и работает быстрее)
SELECT first_name, last_name
FROM customer
UNION ALL
SELECT first_name, last_name
FROM employee;
/*
 Иван,Иванов
Петр,Сергеев
Ирина,Бросова
Анна,Опушкина
Потап,Потапов
Кристина,Позова
Михаил,Кругов
Анна,Опушкина
Иван,Иванов
Сергей,Петров
 */

-- так будут в 1 табличке все имена, КРОМЕ тех что есть во второй (типа взаимоисключения, которые не повторяются)
SELECT first_name, last_name
FROM customer
EXCEPT
SELECT first_name, last_name
FROM employee;
/*
 Потап,Потапов
Ирина,Бросова
Петр,Сергеев
 */

-- аналог иннер джойна
SELECT first_name, last_name
FROM customer
INTERSECT
SELECT first_name, last_name
FROM employee;
/*
 Анна,Опушкина
Иван,Иванов
 */

CREATE TABLE referrer
(
    first_name text,
    last_name  text
);

INSERT INTO referrer
VALUES ('Евгений', 'Онегин'),
       ('Петр', 'Сергеев'),
       ('Александр', 'Ожегов'),
       ('Анна', 'Опушкина'),
       ('Михаил', 'Кругов');

--демонстрация безобразия с еще 1 таблицей. причем без дубликатов, ибо юнион (здесь вообще все)
SELECT first_name, last_name
FROM customer
UNION
SELECT first_name, last_name
FROM employee
UNION
SELECT first_name, last_name
FROM referrer
ORDER BY first_name, last_name;
-- Александр,Ожегов
-- Анна,Опушкина
-- Евгений,Онегин
-- Иван,Иванов
-- Ирина,Бросова
-- Кристина,Позова
-- Михаил,Кругов
-- Петр,Сергеев
-- Потап,Потапов
-- Сергей,Петров

-- теперь с эксцептом (т.е. все те которых НЕТ в объединенной первой) - т.е. например анны здесь не будет
SELECT first_name, last_name
FROM customer
UNION ALL
SELECT first_name, last_name
FROM employee
EXCEPT
SELECT first_name, last_name
FROM referrer
ORDER BY first_name, last_name;
/*
 Иван,Иванов
Ирина,Бросова
Кристина,Позова
Потап,Потапов
Сергей,Петров
 */

-- можно менять порядок выборки. здесь:
SELECT first_name, last_name
FROM employee
EXCEPT
SELECT first_name, last_name
FROM referrer;
/*
 Кристина,Позова
Сергей,Петров
Иван,Иванов
 */

SELECT first_name, last_name
FROM customer
UNION ALL
(SELECT first_name, last_name
 FROM employee
 EXCEPT
 SELECT first_name, last_name
 FROM referrer)
ORDER BY first_name, last_name;
/*
 Анна,Опушкина
Иван,Иванов
Иван,Иванов
Ирина,Бросова
Кристина,Позова
Петр,Сергеев
Потап,Потапов
Сергей,Петров
 */