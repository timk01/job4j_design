-- базовая таблица - та же
create table products
(
    id       serial primary key,
    name     varchar(50),
    producer varchar(50),
    count    integer default 0,
    price    integer
);

insert into products (name, producer, count, price)
VALUES ('product_1', 'producer_1', 3, 50);
insert into products (name, producer, count, price)
VALUES ('product_2', 'producer_2', 15, 32);
insert into products (name, producer, count, price)
VALUES ('product_3', 'producer_3', 8, 115);

begin transaction;

select *
from products;
-- 5 продуктов

update products
set count = count * 10
where count <= 10;
-- 30, 80 (2 продукта)

savepoint point_after_count_change;

-- допустим, 2 новых тестовых продукта вкинули на рынок (магазин), чтобы оценить спрос
insert into products (name, producer, count, price)
values ('new_test_product1', 'old_producer_1', 10, 100),
       ('new_test_product2', 'old_producer_2', 10, 150);

savepoint point_after_goods_addition;

delete
from products
where price = 115;

savepoint point_after_goods_deletion_by_price;

rollback to point_after_count_change;

rollback to point_after_goods_addition;

rollback to point_after_goods_deletion_by_price;

-- не слишком удобная штука, учитывая что при малейшем сбое наблюдается роллбек...
-- но роллбек к какой-то точке остановы затирает последующие точки остановы, тоже нюанс
