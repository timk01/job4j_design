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

-- alternative one with union
select t1.name as "first name",
       t2.name as "second name"
from teens t1
         join teens t2 on t1.name <= t2.name
where t1.gender != t2.gender
union
select t1.name as "first name",
       t2.name as "second name"
from teens t1
         join teens t2 on t1.name < t2.name
where t1.gender != t2.gender;

select distinct
    case when t.name<=t2.name then t.name else t2.name end as "first name",
    case when t.name<=t2.name then t2.name  else t.name end as "second name"
from teens t
         cross join teens t2
where t.gender != t2.gender;

-- with where only
select t1.name as "first name",
       t2.name as "second name"
from teens t1
         cross join teens t2
where t1.name != t2.name and t1.gender != t2.gender and t1.name < t2.name;
/*
Ava,Liam
Ava,Noah
Emma,Liam
Emma,Noah
Liam,Olivia
Noah,Olivia
(all 3 cases)
 */