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

create
    or replace function tax()
    returns trigger as
$$
BEGIN
    update products
    set price = price - price * 0.2
    where id = (select id from inserted)
      and count <= 5;
    return new;
END;
$$
    LANGUAGE 'plpgsql';

create trigger tax_trigger
    after insert
    on products
    referencing new table as
        inserted
    for each statement
execute procedure tax();