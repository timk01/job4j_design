-- LEFT OUTER join
-- мы присоединяем к ЛЕВОЙ таблице (главной) правую. показывается все что есть в левой и правой,
-- даже если у последней нет например поля (владельца)
select * from devices d
         left join owners o on d.owner_id = o.id;

-- все нулль-владельцы (или девайсы без владельца)
select * from devices d
                  left join owners o on d.owner_id = o.id
where o.id is null;

-- все те владельцы, у которых есть девайсы (их 3, но отображалсся 4, т.к. у 1 владельца 2 девайса)
-- т.е. да, внезапно, если бы владелец был нулем, он бы тут отображалсся
select * from owners o
         left join devices d on o.id = d.owner_id;

-- RIGHT OUTER join
-- same shit, but the other table (owners) is main now
select * from owners o
                  right join devices d on d.owner_id = o.id;

select * from owners o
                  left join devices d on o.id = d.owner_id;

-- FULL OUTER join
-- full join is ccccombo from first select and the second one.
-- in result we see both selects in talbe
select * from devices d
                  full join owners o on d.owner_id = o.id;

-- the same as union
select * from devices d
                  left join owners o on d.owner_id = o.id
union
select * from devices d
                  right join owners o on d.owner_id = o.id;

-- CROSS JOIN = ALL pairs of elems (matrix)
-- all possible owners for devices (kinda like multiple, 3*6 = 18)
-- see example in cross_join.sql (mult. table)
select * from devices d
                  cross join owners o;

-- Таким образом, данный вид запроса может служить для генерации данных на уровне БД.
-- (cross join ~ loop with random data)