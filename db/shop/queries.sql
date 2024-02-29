-- 1. Написать запрос получение всех продуктов с типом "СЫР"
select p.name,
       p.price
from product as p
         join type as t on p.type_id = t.id
where t.name = 'Сыр';

-- 2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженое"
select p.name,
       p.price
from product as p
where p.name like '%мороженое%';

-- 3. Написать запрос, который выводит все продукты, срок годности которых уже истек
select p.name,
       p.price,
       p.expired_date
from product as p
where p.expired_date < current_date + interval '1 day';

-- 4. Написать запрос, который выводит самый дорогой продукт.
-- Запрос должен быть универсальный и находить все продукты с максимальной ценой.
-- ((т.е. видимо самый дорогой продукт ИЗ категории))
-- ВАЖНО: группировка осуществлеяется по типу продукта (а их может быть несколько)
-- которому соответствует максимальная цена из прайса (сравниваются все продукты из категории и находится самый дорогой)
select t.name       as "Тип продукта",
       max(p.price) as "Максимальная цена"
from type as t
         join
     product as p on t.id = p.type_id
GROUP BY t.name;

-- 5. Написать запрос, который выводит для каждого типа количество продуктов к нему принадлежащих.
-- В виде имя_типа, количество
select t.name as "Тип продукта",
       count(p.name)
from type as t
         join product p on t.id = p.type_id
group by t.name;

-- 6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
select p.name,
       p.price,
       p.expired_date
from product as p
         join type t on p.type_id = t.id
where t.name in ('Сыр', 'Молоко');

-- 7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
-- Под количеством подразумевается количество продуктов определенного типа.
-- Например, если есть тип "СЫР" и продукты "Сыр плавленный" и "Сыр моцарелла",
-- которые ему принадлежат, то количество продуктов типа "СЫР" будет 2.
select t.name   as "Тип продукта",
       count(*) as Количество
from type as t
         join product p on t.id = p.type_id
group by t.name
having count(*) < 10;

-- 8. Вывести все продукты и их тип.
select p.name,
       p.price,
       p.expired_date,
       t.name
from product as p
         join type t on p.type_id = t.id;

-- 8.5 Вывести все продукты и их тип (аггрегированно)
select t.name,
       array_agg(p.name)
from product as p
         join type t on p.type_id = t.id
group by t.name;