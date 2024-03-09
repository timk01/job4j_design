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

-- same as
-- start transaction
begin transaction;

insert into products (name, producer, count, price) values ('product_4', 'producer_4', 11, 64);

-- same as
-- commit;
commit transaction;

select * from products;

-- прочие команды:

-- set transaction режим_транзакции;
-- set transaction isolation level repeatable read;
-- если глобально на всю сессию (можно тем не менее оверрайдить с set transacton)
-- set session characteristics as transaction isolation level serializable;

-- можно сделать 2 в 1 (как например с веткой в гите) - установить уровень транзакции + начать ее (КРОМЕ снепшота!)
-- start transaction isolation level read committed;
-- со снепшотом только так:
-- set transaction snapshot id_снимка;

-- откат транзакции
-- rollback; // rollback transaction;
-- можно сохранить точку остановы и отроллиться к ней
-- savepoint имя_точки_сохранения;
-- rollback to savepoint имя_точки_сохранения;
-- удалить точку остановы
-- release savepoint имя_точки_сохранения;

begin transaction;

delete from products;

drop table products;

rollback transaction;

-- несмотря на удаление данных и даже таблицы, роллбек работает
select * from products;


begin transaction;

insert into products (name, producer, count, price) VALUES ('product_5', 'producer_5', 17, 45);

-- как раз точка остановы
savepoint first_savepoint;

delete from products where price = 115;
update products set price = 75 where name = 'product_1';

select * from products;
-- здесь будет 4 продукта (1 добавленный 45, 2 бывших 32 и 64, 1 измененный 115 -> 75)

rollback to first_savepoint;

select * from products;
-- а здесь по-прежнему 5 (3+1 бывшие + 1 новый)

commit transaction;

-- !!!
/*
После выполнения команды ROLLBACK TO блок транзакции все еще активен,
поэтому для ее завершения требуется выполнить или COMMIT, или откатить транзакцию полностью с помощью ROLLBACK.
Также стоит обратить внимание, что в блоке транзакции может быть несколько SAVEPOINT.
 */