CREATE TABLE users
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username text NOT NULL
);

INSERT INTO users (username)
SELECT 'person' || n
FROM generate_series(1, 1000) AS n;