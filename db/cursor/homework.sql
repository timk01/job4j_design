/*
вашим заданием будет организовать обратный проход в курсоре (от 20 до 1 записи).

Используйте в своем курсоре опцию SCROLL, перейдите на последнюю запись в таблице, затем перейдите на 15 запись,
 с 15 на 7, а с 7 на 2 и затем на 1. Двигайтесь по курсору назад с помощью FETCH/MOVE.
 */

-- DECLARE cursorname [ BINARY ] [ INSENSITIVE ] [ SCROLL ] CURSOR  FOR query
--  [ FOR { READ ONLY | UPDATE [ OF  column [, ...] ] } ]

-- The SCROLL SQL keyword exists to specify that multiple rows at a time can be selected from the cursor.
-- This is the default in PostgreSQL, even if it is unspecified.

begin;
declare
    backward_products_cursor
    scroll cursor for
    select *
    from products;
fetch last from backward_products_cursor;
-- 20,product_20,20,100
move backward 5 from backward_products_cursor;
fetch from backward_products_cursor; -- 16 poz
move backward from backward_products_cursor;
fetch backward from backward_products_cursor; -- 15 poz
move backward 8 from backward_products_cursor; -- 8 poz
fetch backward from backward_products_cursor; -- 7 poz
move backward 6 from backward_products_cursor; -- 2 poz (if no backwards afterwards, need +1 poz)
fetch from backward_products_cursor; -- 2 poz
fetch backward backward_products_cursor;
-- 1 poz
-- this one reutrns empty entry^
-- fetch backward backward_products_cursor; -- 1 poz
close backward_products_cursor;
commit;
