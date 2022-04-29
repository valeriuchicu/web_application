CREATE TABLE departments
(
    id          NUMBER(8) PRIMARY KEY,
    name        VARCHAR2(30) UNIQUE NOT NULL,
    location VARCHAR2(35) NOT NULL
);

-- -- Starts with 100
CREATE SEQUENCE departments_seq
    START WITH 100
    INCREMENT BY 10
    MAXVALUE 9990
    NOCACHE
    NOCYCLE;

ALTER TABLE departments
    MODIFY id DEFAULT departments_seq.nextval;

BEGIN
    INSERT INTO departments (name, location)
    VALUES (
            'Administration'
           , 'Chisinau');

    INSERT INTO departments (name, location)
    VALUES (
             'Marketing'
           , 'Amsterdam');

    INSERT INTO departments (name, location)
    VALUES (
             'Human Resources'
           , 'Berlin');

    INSERT INTO departments (name, location)
    VALUES (
             'IT'
           , 'Budapest');

    INSERT INTO departments (name, location)
    VALUES (
             'Sales'
           , 'London');

    INSERT INTO departments (name, location)
    VALUES (
             'Finance'
           , 'Venice');

    INSERT INTO departments (name, location)
    VALUES (
             'Payroll'
           , 'Kent');
END;
