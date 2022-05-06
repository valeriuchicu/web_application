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


INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
VALUES (
           'Steven'
       , 'King'
       , 'SKING'
       , '515.123.4567'
       , 24000
       , 1
       );
--
INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
VALUES (
           'Neena'
       , 'Kochhar'
       , 'NKOCHHAR'
       , '515.123.4568'
       , 17000
       , 2);

INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
VALUES (
           'Lex'
       , 'De Haan'
       , 'LDEHAAN'
       , '515.123.4569'
       , 17000
       , 3);