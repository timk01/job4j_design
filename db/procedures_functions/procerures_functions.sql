/*
 create table products
(
    id       serial primary key,
    name     varchar(50),
    producer varchar(50),
    count    integer default 0,
    price    integer
);
 */

create
    or replace procedure insert_data(i_name varchar, prod varchar, i_count integer, i_price integer)
    language 'plpgsql'
as
$$
BEGIN
    insert into products (name, producer, count, price)
    values (i_name, prod, i_count, i_price);
END
$$;

call insert_data('product_2', 'producer_2', 15, 32);

-- При этом процедура будет принимать 3 параметра – количество товара, налог, а также id записи.
-- Если количество товара, передаваемое в метод больше 0,
-- то мы уменьшаем на это количество товара у записи с передаваемым id.
-- Если же налог больше 0, то надо увеличить price у всех записей на сумму налога.
create
    or replace procedure update_data(u_count integer, tax float, u_id integer)
    language 'plpgsql'
as
$$
BEGIN
    if u_count > 0 THEN
        update products
        set count = count - u_count
        where id = u_id;
    end if;
    if
        tax > 0 THEN
        update products
        set price = price + price * tax;
    end if;
END;
$$;

-- учитывая сработку триггеров, у нас 58,product_2,producer_2,15,46
call update_data(10, 0, 58);
-- уменьшилось на 10, т.к. налог = 0, цена без изменений.

-- +чуть полей
call insert_data('product_1', 'producer_1', 3, 50);
call insert_data('product_3', 'producer_3', 8, 115);
-- 59,product_1,producer_1,3,72
-- 60,product_3,producer_3,8,166

-- ВСЕ цены изменятся!
call update_data(0, 0.2, 0);
-- 59,product_1,producer_1,3,86
-- 60,product_3,producer_3,8,199

-- Обновить процедуру (например, переименовать) как и обычно можно с помощью ALTER PROCEDURE
alter procedure update_data(u_count integer, tax float, u_id integer) rename to update;

-- Удалить процедуру можно с помощью DROP. Это будет выглядеть следующим образом:
drop procedure update(u_count integer, tax float, u_id integer);

-- Зачистим таблицу products перед использованием хранимых функций:
delete
from products;
ALTER SEQUENCE products_id_seq RESTART WITH 1;

-- теперь с ФУНКЦИЯМИ
-- Будем выполнять те же действия, что и ранее, добавляем записи в таблицу,
-- а также редактировать существующие записи
-- зрительно то же самое, кроме returns void - функция ОБЯЗАНА возврать значение
create
    or replace function f_insert_data(i_name varchar, prod varchar, i_count integer, i_price integer)
    returns void
    language 'plpgsql'
as
$$
begin
    insert into products (name, producer, count, price)
    values (i_name, prod, i_count, i_price);
end;
$$;

-- Функции, в отличие от процедур, вызываются через обычный SELECT, т.е. следующим образом:
select f_insert_data('product_1', 'producer_1', 25, 50);
-- с учетом триггеров: 1,product_1,producer_1,25,72

-- Теперь перепишем ранее созданную процедуру для редактирования записей в таблице на функцию.
-- При этом она будет с возвращаемым значением.
-- здсь добавлено:
-- returns integer (это просто ЧТО возвращает, т.е. тип)
-- ДО общей логики: (это как обхявоение переменной)
-- declare
--     result integer;
-- в самом конце return result;

-- И ДА! SELECT INTO creates a new table and fills it with data computed by a query.
-- The data is not returned to the client, as it is with a normal SELECT .
--             select into result count
-- после сета данных в оригинальную таблицу еще и создает резалт в другой таблице как каунт
-- + еще и считает сумму цер из продуктов
create
    or replace function f_update_data(u_count integer, tax float, u_id integer)
    returns integer
    language 'plpgsql'
as
$$
declare
    result integer;
begin
    if u_count > 0 THEN
        update products
        set count = count - u_count
        where id = u_id;
        select into result count
        from products
        where id = u_id;
    end if;
    if tax > 0 THEN
        update products
        set price = price + price * tax;
        select into result sum(price)
        from products;
    end if;
    return result;
end;
$$;

select f_update_data(10, 0, 1);
-- после выполнении функции будет:
-- 1,product_1,producer_1,25,72
-- в КОНСОЛЕ будет f_update_count поле с = 15
-- будет обновлено поле count c 15
-- Т.е. обновленное значение количества товаров – 25 – 10 получается 15. Все работает корректно.

-- + чуть данных
select f_insert_data('product_2', 'producer_2', 15, 32);
select f_insert_data('product_3', 'producer_3', 8, 115);
/*
 1,product_1,producer_1,15,72
2,product_2,producer_2,15,46
3,product_3,producer_3,8,166
 */

select f_update_data(0, 0.2, 0);
-- в КОНСОЛЕ будет f_update_count поле с = 340 (сумма колонок цены)
-- будет пересчет цены
/*
 86
55
199
 */

-- Обновить и удалить функции можно аналогичным образом, что и при работе с процедурами.