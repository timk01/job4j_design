-- За основу возьмите таблицу, с которой мы работали в описании.
-- Добавьте процедуру и функцию, которая будет удалять записи.
-- Условия выбирайте сами – удаление по id, удалить если количество товара равно 0 и т.п.

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

-- удалить если количество товара равно нулю
-- procedure
create
    or replace procedure delete_data()
    language 'plpgsql'
as
$$
BEGIN
    delete
    from products
    where count = 0;
END
$$;

ALTER SEQUENCE products_id_seq RESTART WITH 100;

call insert_data('product_2', 'producer_2', 0, 32);
-- 4,product_2,producer_2,0,46 (count = 0)

call delete_data();

select f_insert_data('product_2', 'producer_2', 0, 32);

-- function
-- удаляет ОДНУ запись (т.е. сейчас если там несколько айди с = 0, удалит первую)
-- если нужно удалить много записей, логику надо переделывать
create
    or replace function f_delete_data()
    returns integer
    language 'plpgsql'
as
$$
declare
    result integer;
BEGIN
    select p.id
    into result
    from products as p
    where count = 0;
    delete
    from products
    where id = result;
    return result;
END
$$;

DROP FUNCTION f_delete_data();

select f_delete_data();

-- here we go
select p.id
into result
from products as p
where count = 0;