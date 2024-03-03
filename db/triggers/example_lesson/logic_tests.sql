-- logic for manual test.
-- 1. Drop triggers. Make sure they all dead.
-- 2. Relaunch them after.
drop trigger add_taxPrice_to_every_new_row_trigger on products;
drop trigger add_taxPrice_trigger on products;
drop trigger add_details_of_added_product_to_history_of_price on products;

-- 3. Drop tables for clean result.
truncate products;
truncate history_of_price;

-- 4. Disable all triggers. Just in case> check pgadmin.
alter table products
    disable trigger add_taxPrice_to_every_new_row_trigger;

alter table products
    disable trigger add_taxPrice_trigger;

alter table products
    disable trigger add_details_of_added_product_to_history_of_price;

-- 5. Insert some base initial data to table products
insert into products (name, producer, count, price)
VALUES ('product_3', 'producer_3', 8, 115);

insert into products (name, producer, count, price)
VALUES ('product_1', 'producer_1', 3, 50);

-- 6. Enable first "global" trigger. Add test data. Check results.
-- this trigger works: after insert by updating price (row/rows)
-- You should be able to add one row or rows group
alter table products
    enable trigger add_taxPrice_trigger;

--BEFORE ALL TRIGGERS:
/*
45,product_3,producer_3,8,115
46,product_1,producer_1,3,50
47,product_1,producer_1,3,50
 */

-- A
insert into products (name, producer, count, price)
VALUES ('product_1', 'producer_1', 3, 50);
-- after one solo addition
-- VALID (changed only one solo added row)
/*
 45,product_3,producer_3,8,115
46,product_1,producer_1,3,50
47,product_1,producer_1,3,50
48,product_1,producer_1,3,60
 */

-- B
insert into products (name, producer, count, price)
VALUES ('product_1', 'producer_1', 3, 50),
       ('product_1', 'producer_1', 3, 50),
       ('product_1', 'producer_1', 3, 50);
-- after several rows addition
-- VALID (changed only new added rows)
/*
 45,product_3,producer_3,8,115
46,product_1,producer_1,3,50
47,product_1,producer_1,3,50
48,product_1,producer_1,3,60
51,product_1,producer_1,3,60
50,product_1,producer_1,3,60
49,product_1,producer_1,3,60
 */

-- 7. Enable first "local" trigger. Add test data. Check results.
-- this trigger works: before insert by updating price (row)
-- You should be able to add one row

alter table products
    enable trigger add_taxPrice_to_every_new_row_trigger;

-- table before adding new data (it's already changed by first trigger)
/*
 45,product_3,producer_3,8,115
46,product_1,producer_1,3,50
47,product_1,producer_1,3,50
48,product_1,producer_1,3,60
51,product_1,producer_1,3,60
50,product_1,producer_1,3,60
49,product_1,producer_1,3,60
 */

-- 8. add row.
insert into products (name, producer, count, price)
VALUES ('product_1', 'producer_1', 3, 50);

-- after one addition it's 72. looks legit
-- changed before (to 60, as local trigger), changed after to 72 (global trigger)

/*
 45,product_3,producer_3,8,115
46,product_1,producer_1,3,50
47,product_1,producer_1,3,50
48,product_1,producer_1,3,60
51,product_1,producer_1,3,60
50,product_1,producer_1,3,60
49,product_1,producer_1,3,60
54,product_1,producer_1,3,72
 */

-- 9. Enable second "local" trigger. Add test data. Check results.
-- -- this trigger works: after insert, adding data to new table (supposed to changed 2 times)
alter table products
    enable trigger add_details_of_added_product_to_history_of_price;

-- 10. Let's check result
-- first 100 + 20% (as BEFORE addition),
-- after it adds to history. WHY ? - because even if they both have "after insert", first will do ROW!
insert into products (name, producer, count, price)
VALUES ('product_2', 'producer_2', 1, 100);

-- noob tests to check if naming makes ordering (doesn't work, since statement != row, row has bigger priority)
alter table products
    disable trigger add_details_of_added_product_to_history_of_price;

create trigger add_taxPrice_vrigger
    after insert
    on products
    for each row
execute procedure history_of_price_changes();

insert into products (name, producer, count, price)
VALUES ('product_2', 'producer_2', 1, 100);

alter table products
    disable trigger add_taxPrice_vrigger;

create trigger add_taxPrice_srigger --price
    after insert
    on products
    for each row
execute procedure history_of_price_changes();

insert into products (name, producer, count, price)
VALUES ('product_2', 'producer_2', 1, 100);