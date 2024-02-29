create table numbers
(
    num int unique
);

insert into numbers(num)
values (1);
insert into numbers(num)
values (2);
insert into numbers(num)
values (3);

select n1.num as a, n2.num as b, (n1.num * n2.num) as "a*b="
from numbers n1
         cross join numbers n2;

/*
1,1,1
1,2,2
1,3,3
2,1,2
2,2,4
2,3,6
3,1,3
3,2,6
3,3,9
*/