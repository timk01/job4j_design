/*
     Вывести список всех машин и все привязанные к ним детали.
    Нужно учесть, что каких-то деталей машина может и не содержать.
    В таком случае значение может быть null при выводе (например, название двигателя null);

    Пример "шапки" при выводе:

    id, car_name, body_name, engine_name, transmission_name

    Вывести кузова, которые не используются НИ в одной машине.
    "Не используются" значит, что среди записей таблицы cars отсутствует внешние ключи, ссылающие на таблицу car_bodies.
    Например, Вы добавили в car_bodies "седан", "хэтчбек" и "пикап",
    а при добавлении в таблицу cars указали только внешние ключи на записи "седан" и "хэтчбек".
    Запрос, касающийся этого пункта, должен вывести "пикап", т.к. среди машин нет тех, что обладают таким кузовом;
    Вывести двигатели, которые не используются НИ в одной машине, аналогично п.2;
    Вывести коробки передач, которые не используются НИ в одной машине, аналогично п.2.
 */

/*
 Вывести список всех машин и все привязанные к ним детали.
    Нужно учесть, что каких-то деталей машина может и не содержать.
    В таком случае значение может быть null при выводе (например, название двигателя null);

    Пример "шапки" при выводе:

    id, car_name, body_name, engine_name, transmission_name
 */
select c.id,
       c.name,
       cb.name,
       ce.name,
       ct.name
from cars as c
         left join car_engines ce on c.engine_id = ce.id
         left join car_bodies cb on c.body_id = cb.id
         left join car_transmissions ct on c.transmission_id = ct.id;

/*
 Вывести кузова, которые не используются НИ в одной машине.
    "Не используются" значит, что среди записей таблицы cars отсутствует внешние ключи, ссылающие на таблицу car_bodies.
    Например, Вы добавили в car_bodies "седан", "хэтчбек" и "пикап",
    а при добавлении в таблицу cars указали только внешние ключи на записи "седан" и "хэтчбек".
 */

-- сначала выведем все (видно, что body_id, да и не только отсутствует)
select *
from car_bodies as cb
         left join cars c on c.body_id = cb.id;
/*
 11,Cabriolet,11,Tesla Model S,11,11,
10,Sports Car,12,Audi A5,10,,10
12,Limousine,,,,,
15,Compact Car,,,,,
13,Van,,,,,
14,Truck,,,,,
 */

-- потому здесь и ориентируемся на буквально "пустое тело" машины - на них не ссылается НИ одна машина
-- а значит, они не используются (или как-то так)
select cb.id,
       cb.name
from car_bodies as cb
         left join cars c on c.body_id = cb.id
where c.body_id is null;

-- Вывести двигатели, которые не используются НИ в одной машине, аналогично п.2; (та же логика, что и с пустым телом)
select ce.id,
       ce.name
from car_engines as ce
         left join cars c on c.body_id = ce.id
where c.body_id is null;

-- Вывести коробки передач, которые не используются НИ в одной машине, аналогично п.2. (т.ж.с.)
select ct.id,
       ct.name
from car_transmissions as ct
         left join cars c on c.body_id = ct.id
where c.body_id is null;