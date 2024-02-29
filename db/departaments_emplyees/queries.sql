-- 2. Выполнить запросы с left, right, full, cross соединениями
-- A
select *
from departments as d
         left join employees as e on d.id = e.department_id;

-- B
select *
from employees as e
         left join departments as d on d.id = e.department_id;

-- С
select *
from employees as e
         full join departments as d on d.id = e.department_id;

-- D (4*17=64)
select *
from employees as e
         cross join departments as d;

-- 3. Используя left join найти департаменты, у которых нет работников
select *
from departments as d
         left join employees as e on d.id = e.department_id
where e.name is null;

-- 4. Используя left и right join написать запросы, которые давали бы одинаковый результат
-- (порядок вывода колонок в эти запросах также должен быть идентичный).
select d.name, e.name
from departments as d
         left join employees as e on d.id = e.department_id;

select d.name, e.name
from employees as e
         left join departments as d on d.id = e.department_id;

-- Используя cross join составить все возможные разнополые пары.
-- Исключите дублирование пар вида Вася-Маша и Маша-Вася.
select distinct
        case when t.name<=t2.name then t.name else t2.name end as "first name",
        case when t.name<=t2.name then t2.name else t.name end "second name"
from teens t
cross join teens t2;