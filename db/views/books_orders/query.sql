-- определим список студентов, у которых находится 2 и более книг одного и того же автора.
-- При этом в результирующей таблице должно быть отражено имя студента, количество книг, имя автора.
select s.name, count(a.name), a.name
from students as s
         join orders o on s.id = o.student_id
         join books b on o.book_id = b.id
         join authors a on b.author_id = a.id
group by (s.name, a.name)
having count(a.name) >= 2;

-- create view имя_представления as запрос_select
-- attention on aliases
create view show_students_with_2_or_more_books
as
select s.name as student, count(a.name), a.name as author
from students as s
         join orders o on s.id = o.student_id
         join books b on o.book_id = b.id
         join authors a on b.author_id = a.id
group by (s.name, a.name)
having count(a.name) >= 2;

-- вызов
select * from show_students_with_2_or_more_books;

-- alter view старое_имя rename to новое_имя
alter view show_students_with_2_or_more_books rename to "show_students_2\+_books";
select * from "show_students_2\+_books";

-- drop view имя_представления
drop view "show_students_2\+_books";

-- query
create view show_stOrders_more_1_book_Gogol_Ii
as
SELECT s.id        AS student_id,
       s.name      AS student_name,
       COUNT(o.id) AS total_orders
FROM students s
         JOIN orders o ON s.id = o.student_id
         JOIN books b ON o.book_id = b.id
         JOIN authors a ON b.author_id = a.id
WHERE a.name LIKE '%Гоголь%'
  AND b.name LIKE '%ий%'
GROUP BY s.id, s.name
HAVING COUNT(o.id) > 1
ORDER BY total_orders DESC;