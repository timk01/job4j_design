/*
1) Извлечение данных, у которых имя name содержит подстроку fish

2) Извлечение данных, у которых сред. продолжительность жизни находится в диапазоне 10 000 и 21 000

3) Извлечение данных, у которых дата открытия не известна (null)

4) Извлечение данных видов, у которых дата открытия раньше 1950 года

5) Написать 3 запроса с inner join с использованием альясов
*/

-- Извлечение данных, у которых имя name содержит подстроку fish
select
    *
from
    fauna
where
    name like '%fish%';

-- Извлечение данных, у которых сред. продолжительность жизни находится в диапазоне 10 000 и 21 000
select
    *
from
    fauna
where
    avg_age between 10000 and 21000;

-- Извлечение данных, у которых дата открытия не известна (null)
select
    *
from
    fauna
where
    discovery_date is null;

-- Извлечение данных видов, у которых дата открытия раньше 1950 года
select
    name, discovery_date
from
    fauna
where
    discovery_date < '01.01.1950';

-- Написать 3 запроса с inner join с использованием альясов
-- A c грппировкой в массив по имени и возрасту ученых, открывших вид
select
    f.name as "specie name",
    f.avg_age,
    ARRAY_AGG(s.name || ' ' || s.second_name) as "full scientist name",
    ARRAY_AGG(s.speciality)
from
    fauna as f
inner join
    scientists as s on f.id = s.fauna_id
group by
    f.name, f.avg_age;

-- Б просто с джойном по дате больше определенной
select
    f.name,
    f.discovery_date,
    s.name || ' ' || s.second_name as "full scientist name",
    s.birthday
from
    fauna as f
inner join
    scientists s on f.id = s.fauna_id
where
    discovery_date > '2017.01.01';

-- C сортировкой по имени
select
    f.name,
    f.discovery_date,
    s.name || ' ' || s.second_name as "full scientist name",
    s.birthday
from
    fauna as f
        inner join
    scientists s on f.id = s.fauna_id
where
    f.name ilike 'li%' or f.name ilike 'ti%' and f.discovery_date is not null
order by
    "full scientist name" desc;