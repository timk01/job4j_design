/*
CREATE FUNCTION trigger_function()
   RETURNS TRIGGER
   LANGUAGE PLPGSQL
AS $$
BEGIN
   -- trigger logic
END;
$$
*/

/*
A trigger function is similar to a regular user-defined function.
However, a trigger function does not take any arguments and has a return value with the type trigger.
*/

/*
After creating a trigger function, you can bind it to one or more trigger events such as INSERT, UPDATE, and DELETE.
*/

/*
First, provide the name of the trigger after the TRIGGER keywords.

Second, indicate the timing that causes the trigger to fire. It can be BEFORE or AFTER an event occurs.

Third, specify the event that invokes the trigger. The event can be INSERT , DELETE, UPDATE or TRUNCATE.

Fourth, specify the name of the table associated with the trigger after the ON keyword.

Fifth, define the type of triggers, which can be:

    The row-level trigger that is specified by the FOR EACH ROW clause.
    The statement-level trigger that is specified by the FOR EACH STATEMENT clause.

A row-level trigger is fired for each row while a statement-level trigger is fired for each transaction.

Finally, give the name of the trigger function after the EXECUTE PROCEDURE keywords.
*/

/*
CREATE TRIGGER trigger_name
   {BEFORE | AFTER} { event }
   ON table_name
   [FOR [EACH] { ROW | STATEMENT }]
       EXECUTE PROCEDURE trigger_function
*/

CREATE TABLE employees
(
    id         INT GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(40) NOT NULL,
    last_name  VARCHAR(40) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS employees;

CREATE TABLE employee_audits
(
    id          INT GENERATED ALWAYS AS IDENTITY,
    employee_id INT         NOT NULL,
    last_name   VARCHAR(40) NOT NULL,
    changed_on  TIMESTAMP   NOT NULL
);

CREATE OR REPLACE FUNCTION log_last_name_changes()
    RETURNS TRIGGER
AS
$$
BEGIN
    IF NEW.last_name <> OLD.last_name THEN
        INSERT INTO employee_audits(employee_id, last_name, changed_on)
        VALUES (OLD.id, OLD.last_name, now());
    END IF;

    RETURN NEW;
END;
$$
LANGUAGE 'PLPGSQL';

CREATE TRIGGER last_name_changes
    BEFORE UPDATE
    ON employees
    FOR EACH ROW
EXECUTE PROCEDURE log_last_name_changes();

INSERT INTO employees (first_name, last_name)
VALUES ('John', 'Doe');

INSERT INTO employees (first_name, last_name)
VALUES ('Lily', 'Bush');

-- Suppose that Lily Bush changes her last name to Lily Brown.
UPDATE employees
SET last_name = 'Brown'
WHERE ID = 2;

-- будет срабатывать триггер, который ДО апдейта для каждого измененного ряда таблицы эмплоиис
-- а внутри, если ластНейм другой (т.е. мы его меняем),
-- он сует микс из старой инфы и новой в другую таблику.

-- взрыв мозга
