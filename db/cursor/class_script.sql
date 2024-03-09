drop table products;

create table products
(
    id    serial primary key,
    name  varchar(50),
    count integer default 0,
    price integer
);

insert into products (name, count, price)
VALUES ('product_1', 1, 5);
insert into products (name, count, price)
VALUES ('product_2', 2, 10);
insert into products (name, count, price)
VALUES ('product_3', 3, 15);
insert into products (name, count, price)
VALUES ('product_4', 4, 20);
insert into products (name, count, price)
VALUES ('product_5', 5, 25);
insert into products (name, count, price)
VALUES ('product_6', 6, 30);
insert into products (name, count, price)
VALUES ('product_7', 7, 35);
insert into products (name, count, price)
VALUES ('product_8', 8, 40);
insert into products (name, count, price)
VALUES ('product_9', 9, 45);
insert into products (name, count, price)
VALUES ('product_10', 10, 50);
insert into products (name, count, price)
VALUES ('product_11', 11, 55);
insert into products (name, count, price)
VALUES ('product_12', 12, 60);
insert into products (name, count, price)
VALUES ('product_13', 13, 65);
insert into products (name, count, price)
VALUES ('product_14', 14, 70);
insert into products (name, count, price)
VALUES ('product_15', 15, 75);
insert into products (name, count, price)
VALUES ('product_16', 16, 80);
insert into products (name, count, price)
VALUES ('product_17', 17, 85);
insert into products (name, count, price)
VALUES ('product_18', 18, 90);
insert into products (name, count, price)
VALUES ('product_19', 19, 95);
insert into products (name, count, price)
VALUES ('product_20', 20, 100);

-- все безобразие происходит внутри транзакции
BEGIN;
DECLARE
    cursor_products cursor for
    select *
    from products;

-- первые 10 из продуктов
FETCH 10 FROM cursor_products;

--еще 2
FETCH NEXT FROM cursor_products;
FETCH NEXT FROM cursor_products;

-- курсор можно передвигать
MOVE FORWARD 2 FROM cursor_products;

-- т.е. передвинули курсор с 12 на 14 (пропустили 2 строки) и сделали выборку следующей строки (15)
FETCH NEXT FROM cursor_products;

-- важно не забыть закрыть курсор
CLOSE cursor_products;

-- + закрыть транзакцию (курсор мы же открывали в ней!)
commit;