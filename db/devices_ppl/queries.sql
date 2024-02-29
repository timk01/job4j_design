-- 3. Используя агрегатные функции вывести среднюю цену устройств.
/*
Smartphone,800
Camera,700
Smartwatch,300
Tablet,500
Laptop,1000
Headphones,200
 */
select d.name,
       avg(d.price)
from devices as d
group by d.name;

-- 4. Используя группировку вывести для каждого человека среднюю цену его устройств.
/*
Emma Johnson,500
Sophia Wilson,600
Olivia Jones,600
Benjamin Moore,600
John Smith,625
Michael Davis,600
Emily Taylor,825
Daniel Brown,366.6666666666667
William Miller,500
Ava Robinson,366.6666666666667
 */
select p.name,
       avg(d.price) as "avg price"
from devices as d
         join devices_people as dp on d.id = dp.device_id
         join people as p on dp.people_id = p.id
group by p.name;

-- 5. Дополнить запрос п.4. условием, что средняя стоимость устройств должна быть больше 5000.
select p.name,
       avg(d.price) as "avg price"
from devices as d
         join devices_people as dp on d.id = dp.device_id
         join people as p on dp.people_id = p.id
group by p.name
having avg(d.price) > 5000;



select p.name,
       avg(d.price) as "avg price"
from devices as d
         join devices_people as dp on d.id = dp.device_id
         join people as p on dp.people_id = p.id
group by p.name
having avg(d.price) > 5000;

-- а вот если сумма устрйоств, то картинка поменяется
select p.name,
       sum(d.price) as "avg price"
from devices as d
         join devices_people as dp on d.id = dp.device_id
         join people as p on dp.people_id = p.id
group by p.name
having sum(d.price) > 2000;
/*
 John Smith,9500
Emily Taylor,8800
Daniel Brown,7100
William Miller,2500
Ava Robinson,7600
 */

-- так пожалуй, лучше всего.
select p.name,
       count(d.price) as quantity,
       sum(d.price) as "avg price"
from devices as d
         join devices_people as dp on d.id = dp.device_id
         join people as p on dp.people_id = p.id
group by p.name
having sum(d.price) > 50;
/*
 Emma Johnson,2,1000
Sophia Wilson,3,1800
Olivia Jones,3,1800
Benjamin Moore,3,1800
John Smith,5,9500
Michael Davis,3,1800
Emily Taylor,5,8800
Daniel Brown,4,7100
William Miller,5,2500
Ava Robinson,4,7600
 */