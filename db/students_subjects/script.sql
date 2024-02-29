create table students
(
    id   serial primary key,
    name text
);

create table subjects
(
    id   serial primary key,
    name text
);

create table students_subjects
(
    id         serial primary key,
    mark       float,
    student_id int references students (id),
    subject_id int references subjects (id)
);

insert into students(name)
values ('Аня'),
       ('Ваня'),
       ('Боря');

insert into subjects(name)
values ('Математика'),
       ('Русский'),
       ('Информатика');

insert into students_subjects(student_id, subject_id, mark)
values (1, 1, 5),
       (1, 2, 5),
       (1, 3, 5);

insert into students_subjects(student_id, subject_id, mark)
values (2, 1, 5),
       (2, 2, 4),
       (2, 3, 4);

insert into students_subjects(student_id, subject_id, mark)
values (3, 1, 3),
       (3, 2, 5),
       (3, 3, 3);

-- можно вызвать агрегатную функцию просто для какого-то столбца
-- агрегатная - аверага, мин/макс, каунт, сумма (аналогично стримам reduce())
-- select... from...
select avg(mark)
from students_subjects;

select min(mark)
from students_subjects;

select max(mark)
from students_subjects;

select count(mark)
from students_subjects;

select sum(mark)
from students_subjects;

-- так, я просто подсоединю данные джойном, не группируя их
-- - т.е. будет по 3 сабжекта у каждого ученика (итого 9)
select
    s.name, ss.mark
from
    students_subjects as ss
        join
    subjects as s on ss.subject_id = s.id;

-- здесь я выбираю столбец по которому делается группировка (group by)
-- И делаю агрегатную функцию по каким-либо данным (здесь среднее арифм по оценкам)
-- т.е. в изначальном варианте будет (признак, (элемент1, …, элементN))
/*
Математика,4.333333333333333
Информатика,4
Русский,4.666666666666667
 */
-- здесь сама группировка делается по имени предмета, а то что мы гурппируем - это оценка
-- НАПРИМЕР. имя - их 3, а оценок - больше. на каждое имя есть больше одной оценки

-- The GROUP BY clause divides the rows by the values in the columns
-- specified in the GROUP BY clause and calculates a value for each group.

-- GROUP BY clause groups rows into groups by the values in the column1
-- а сама функция авг (или любая другая агрегатная) объединяет ряды какого-то столбца и что-то с ними делает
select
        s.name, avg(ss.mark)
from
        students_subjects as ss
            join
                subjects as s on ss.subject_id = s.id
group by
        s.name;

-- здесь то же самое, но по имени уже студента и его средним оценкам
/*
 Ваня,4.333333333333333
Боря,3.6666666666666665
Аня,5
 */
select
        s.name, avg(ss.mark)
from
        students_subjects as ss
            join
                students s on ss.student_id = s.id
group by
        s.name;

-- здесь просто хэвинг
-- И having (который ОК для агрегатный данных, но не ОК для обычных) - аналог where, но уже для агрегатных данных
select
        s.name, avg(ss.mark)
from
        students_subjects as ss
            join
                subjects as s on ss.subject_id = s.id
group by
        s.name
having
        avg(ss.mark) > 4.2;

-- здесь я делаю И where (который для обычных данных, но не ОК дял агрегатных),
-- И having (который ОК для агрегатный данных, но не ОК для обычных)
select
        s.name, avg(ss.mark)
from
        students_subjects as ss
            join
                subjects as s on ss.subject_id = s.id
where
        s.name = 'Информатика'
group by
        s.name
having
        avg(ss.mark) > 3.2;