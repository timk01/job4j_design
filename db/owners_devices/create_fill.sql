create table owners
(
    id   serial primary key,
    name varchar(255)
);

create table devices
(
    id       serial primary key,
    name     varchar(255),
    owner_id int references owners (id)
);

insert into owners(name)
values ('Owner 1');
insert into owners(name)
values ('Owner 2');
insert into owners(name)
values ('Owner 3');

insert into devices(name, owner_id)
values ('Device 1', 1);
insert into devices(name, owner_id)
values ('Device 2', 2);
insert into devices(name, owner_id)
values ('Device 3', 3);
insert into devices(name, owner_id)
values ('Device 4', null);
insert into devices(name, owner_id)
values ('Device 5', null);
insert into devices(name, owner_id)
values ('Device 6', 1);

