-- базовое заполнение таблицы данными (вызывается для каждой степени изолированности)
CREATE TABLE accounts
(
    id      SERIAL PRIMARY KEY,
    balance INTEGER
);

INSERT INTO accounts (balance)
VALUES (1000),
       (2000),
       (3000);

-- А. Uncommited Read. Работает криво.
-- Суть - мы видим ппочти все (кроме апдейтов - которые Повторяющееся чтение) изменения во 2 таблице
-- здесь и далее выполнять операции параллельно для просмотра иземнений
begin transaction isolation level read uncommitted;

select *
from accounts;

-- добавление данных (изменение, удаление) в первой - видно во второй. даже и без коммита, ага
insert into accounts (balance)
VALUES (10000);
delete
from accounts
where balance = 1000;
update accounts
set balance = 30000
where balance = 3000;

select *
from accounts;
-- измененные состояния здесь

-- Б. READ COMMITTED - когда есть коммит. Нет коммита - нет изменений.
delete
from accounts;

SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
-- можно и без нее, т.к. оно по дефолту в постгрях

-- добавление данных (изменение, удаление) в первой - НЕ видно во второй, пока нет коммита
insert into accounts (balance)
VALUES (10000);
delete
from accounts
where balance = 1000;
update accounts
set balance = 30000
where balance = 3000;

-- в первой и во второй после коммита видны данные. измененные.
commit;

select *
from accounts;

-- В. repeatable read
-- вообще говоря, в постгрях (именно в них), фиксит стандартные аномалии. но кое-что все же остается, такие дела...
-- т.е. говорить, что полностью делает свою работу, монжо лишь с натяжкой
delete
from accounts;

begin transaction isolation level repeatable read;

-- это в первой
insert into accounts (balance)
VALUES (10000);
delete
from accounts
where balance = 1000;
update accounts
set balance = 30000
where balance = 3000;

-- это уже во второй
update accounts
set balance = 30000
where balance = 3000;

--тут 2 варианта. В общес случае, 2 будет ждать с локом
-- И в этом случае мы получаем LOCK: вторая транзакция будет ждать,
-- пока первая транзакция не зафиксирует изменения или не откатится.

-- 1) откат - у первой - измененное состояние у второй (ну и у первой после)
rollback;

-- 2) фиксация изменений у первой - ошибка у второй в плане: нельзя одновременно
commit;

-- Д. serializable. Суть - последвоательные транзакции. Очередь.
delete
from accounts;

begin transaction isolation level serializable;

-- это в первой
insert into accounts (balance)
VALUES (10000);
delete
from accounts
where balance = 1000;
update accounts
set balance = 30000
where balance = 3000;

-- это во второй
insert into accounts (balance)
VALUES (10000);
delete
from accounts
where balance = 1000;
update accounts
set balance = 30000
where balance = 3000;

-- в первой - ведет на ошибку во второй:
/*
delete from accounts where balance = 1000;
ОШИБКА:  не удалось сериализовать доступ из-за параллельного удаления
postgres=!# update accounts set balance = 30000 where balance = 3000;
ОШИБКА:  текущая транзакция прервана, команды до конца блока транзакции игнорируются
*/
commit;