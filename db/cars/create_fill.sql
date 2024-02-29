-- Создание таблицы car_bodies
CREATE TABLE car_bodies
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Заполнение таблицы car_bodies
INSERT INTO car_bodies (name)
VALUES ('Sedan'),
       ('SUV'),
       ('Coupe'),
       ('Hatchback'),
       ('Convertible'),
       ('Minivan'),
       ('Pickup'),
       ('Crossover'),
       ('Wagon'),
       ('Sports Car'),
       ('Cabriolet'),
       ('Limousine'),
       ('Van'),
       ('Truck'),
       ('Compact Car');

-- Создание таблицы car_engines
CREATE TABLE car_engines
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

-- Заполнение таблицы car_engines
INSERT INTO car_engines (name)
VALUES ('V4 Engine'),
       ('V6 Engine'),
       ('V8 Engine'),
       ('Inline-4 Engine'),
       ('Inline-6 Engine'),
       ('Inline-8 Engine'),
       ('Electric Motor'),
       ('Hybrid Engine'),
       ('Rotary Engine'),
       ('Diesel Engine'),
       (NULL);

-- Создание таблицы car_transmissions
CREATE TABLE car_transmissions
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

-- Заполнение таблицы car_transmissions
INSERT INTO car_transmissions (name)
VALUES ('Automatic Transmission'),
       ('Manual Transmission'),
       ('CVT (Continuously Variable Transmission)'),
       ('Semi-Automatic Transmission'),
       ('Dual-Clutch Transmission'),
       ('Automated Manual Transmission'),
       ('Tiptronic Transmission'),
       ('Hydrostatic Transmission'),
       ('Direct-Shift Gearbox (DSG)'),
       ('e-CVT (Electronic Continuously Variable Transmission)'),
       (NULL);

-- Создание таблицы cars
CREATE TABLE cars
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    body_id         INT REFERENCES car_bodies (id),
    engine_id       INT REFERENCES car_engines (id),
    transmission_id INT REFERENCES car_transmissions (id)
);

-- Заполнение таблицы cars
INSERT INTO cars (name, body_id, engine_id, transmission_id)
VALUES ('Toyota Camry', 1, 1, 1),
       ('Ford Explorer', 2, 2, 2),
       ('Chevrolet Corvette', 3, 3, 3),
       ('Volkswagen Golf', 4, 4, 4),
       ('Mazda MX-5 Miata', 5, 5, 5),
       ('Honda Odyssey', 6, 6, 6),
       ('Ford F-150', 7, 7, 7),
       ('Nissan Rogue', 8, 8, 8),
       ('Subaru Outback', 9, 9, 9),
       ('Porsche 911', 10, 10, 10),
       ('Tesla Model S', 11, 11, NULL),
       ('Audi A5', 10, NULL, 10),
       ('Lamborghini Huracan', NULL, 10, 10);

-- это для того, чтобы было, что выводить как "не испольуземое ни в 1 машине"
INSERT INTO car_engines (name)
VALUES ('Magic Engine');

INSERT INTO car_transmissions (name)
VALUES ('Super-mega-transmission');