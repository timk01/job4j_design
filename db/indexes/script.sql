create table people
(
    id         serial primary key,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    phone      VARCHAR(50)
);

-- структуру созданной таблицы можно прочитать если в консоли выполнить команду:
-- ТОЛЬКО ДЛЯ PSQL
-- \d+ people

-- так - общую инфу
SELECT column_name,
       data_type,
       character_maximum_length,
       is_nullable,
       column_default
FROM information_schema.columns
WHERE table_name = 'people';

--или атк
select column_name, data_type, character_maximum_length, column_default, is_nullable
from INFORMATION_SCHEMA.COLUMNS
where table_name = 'people';

-- а вот так - индексы!
select *
from pg_indexes
where tablename = 'people';
-- CREATE UNIQUE INDEX people_pkey ON public.people USING btree (id)
-- это, стало быть, КЛАСТЕРНЫЙ индекс
-- Мы видим, что создался индекс на основании первичного ключа с названием people_key и он имеет тип btree,
-- строится он на основании поля id.

-- теперь про создание НЕКЛАСТЕРНОГО (СВОЕГО) индекса
-- Чтобы создать ключ вручную (при этом будет создан некластерный индекс) используется команда:
-- create index имя_индекса on имя_таблицы(имя_столбца);

-- по столку last_name с сортировкой по убыванию
create index people_last_name on people(last_name desc);

select *
from pg_indexes
where tablename = 'people';
-- CREATE UNIQUE INDEX people_pkey ON public.people USING btree (id)
-- CREATE INDEX people_last_name ON public.people USING btree (last_name DESC)
-- в-общем команда в indexdef графе повторяет надпись выше

-- alter index people_last_name rename to people_last_name_desc;
-- changename (index)

alter index people_last_name rename to people_last_name_desc;

-- так "дропать":
drop index people_last_name_desc;

-- отдельная тема, когда использовать кластерные/некластерные индексы
-- (дроубек - замедление вставок в БД данных, но плюсы - сильно быстрее поиск, это типа... массива)

EXPLAIN SELECT * FROM people;

-- хорошая команда для измерения времени (подъодит, когда смотрим время индеексов-не индексов)
explain select * from people where last_name = 'smth'; -- people_last_name_desc;
-- Index Scan using people_last_name_desc on people  (cost=0.14..8.16 rows=1 width=358)