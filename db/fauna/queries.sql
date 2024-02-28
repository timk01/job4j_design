/*
1) Извлечение данных, у которых имя name содержит подстроку fish

2) Извлечение данных, у которых сред. продолжительность жизни находится в диапазоне 10 000 и 21 000

3) Извлечение данных, у которых дата открытия не известна (null)

4) Извлечение данных видов, у которых дата открытия раньше 1950 года
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