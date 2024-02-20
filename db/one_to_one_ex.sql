CREATE TABLE Student
(
    student_id   INT PRIMARY KEY,
    student_name VARCHAR(50),
    passport_id  INT UNIQUE,
    FOREIGN KEY (passport_id) REFERENCES Passport(passport_id)
);

CREATE TABLE Passport
(
    passport_id     INT PRIMARY KEY,
    passport_number VARCHAR(20) UNIQUE
);