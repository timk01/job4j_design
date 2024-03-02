-- see the elsson for details

create table products
(
    id       serial primary key,
    name     varchar(50),
    producer varchar(50),
    count    integer default 0,
    price    integer
);

create
    or replace function discount()
    returns trigger as
$$
BEGIN
    update products
    set price = price - price * 0.2
    where count <= 5
      AND id = new.id;
    return NEW;
END;
$$
    LANGUAGE 'plpgsql';

create trigger discount_trigger
    after insert
    on products
    for each row
execute procedure discount();

alter table products
    disable trigger discount_trigger;

alter table products
    enable trigger discount_trigger;

drop trigger discount_trigger on products;

drop trigger tax_trigger on products;

create
    or replace function tax()
    returns trigger as
$$
BEGIN
    update products
    set price = price - price * 0.2
    WHERE id IN (SELECT id FROM inserted)
      AND count <= 5;
    return new;
END;
$$
    LANGUAGE 'plpgsql';

create trigger tax_trigger
    after insert --INSERT OR UPDATE ON
    on products
    referencing new table as
        inserted
    for each statement
execute procedure tax();

--temporary OFF ALL trigers
alter table products
    disable trigger discount_trigger;

alter table products
    disable trigger tax_trigger;

alter table products
    disable trigger add_taxPrice_trigger;

drop trigger add_taxPrice_trigger on products;

--ON tax_trigger to test it
alter table products
    enable trigger tax_trigger;

-- 1)  Триггер должен срабатывать после вставки данных, для любого товара и просто насчитывать налог на товар
-- (нужно прибавить налог к цене товара). Действовать он должен не на каждый ряд, а на запрос (statement уровень)
create trigger add_taxPrice_trigger
    after insert
    on products
    referencing new table as
        inserted
    for each statement
execute procedure tax_on_price();

create
    or replace function tax_on_price()
    returns trigger as
$$
BEGIN
    update products
    set price = price + price * 0.2
    where id IN (select id from inserted);
    return null;
END;
$$
    LANGUAGE 'plpgsql';

--test
insert into products (name, producer, count, price)
VALUES ('product_1', 'producer_1', 3, 50),
       ('product_1', 'producer_1', 3, 50),
       ('product_1', 'producer_1', 3, 50);

--    2) Триггер должен срабатывать до вставки данных и насчитывать налог на товар
--    (нужно прибавить налог к цене товара). Здесь используем row уровень.
-- еще раз! ROW - на новую строчку. ОДНУ.
drop trigger add_taxPrice_to_every_new_row_trigger on products;

create trigger add_taxPrice_to_every_new_row_trigger
    before insert
    on products
    for each row
execute procedure tax_on_row_price();

create
    or replace function tax_on_row_price()
    returns trigger as
$$
BEGIN
    update products
    set price = price + price * 0.2
    where id = new.id;
    return new;
END;
$$
    LANGUAGE 'plpgsql';

insert into products (name, producer, count, price)
VALUES ('product_1', 'producer_1', 3, 100);

/*
         3) Создайте таблицу:
         (ниже)

           Нужно написать триггер на row уровне, который сразу после вставки продукта в таблицу products,
         будет заносить имя, цену и текущую дату в таблицу history_of_price.
 */
create table history_of_price
(
    id    serial primary key,
    name  varchar(50),
    price integer,
    date  timestamp
);

create trigger add_details_of_added_product_to_history_of_price
    after insert
    on products
    for each row
execute procedure history_of_price_changes();

create
    or replace function history_of_price_changes()
    returns trigger as
$$
BEGIN
    INSERT INTO history_of_price(name, price, date)
    VALUES (new.name, new.price, now());
    return new;
END;
$$
    LANGUAGE 'plpgsql';

--test
insert into products (name, producer, count, price)
VALUES ('product_2', 'producer_2', 5, 50);