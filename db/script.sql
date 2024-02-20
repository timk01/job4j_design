create table human(
	id serial primary key,
	credentials varchar(64),
	sex char(2),
	birthday timestamp,
	register_date date
);
insert into human (credentials, sex, birthday, register_date)
values(
	'Ivanov Ivan Ivanovich',
	'ml',
	'2000-01-28 08:00:04',
	'2024-02-20'
	);
select * from human;
update human
set credentials = 'Ivanov Ivan Ivanovitch';
select * from human;
delete from human;
select * from human;