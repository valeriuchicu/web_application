CREATE TABLE employees
(
    employee_id   NUMBER(4) PRIMARY KEY,
    first_name    VARCHAR2(20)        NOT NULL,
    last_name     VARCHAR2(25)        NOT NULL,
    email         VARCHAR2(25) UNIQUE NOT NULL,
    phone_number  VARCHAR2(20) UNIQUE NOT NULL,
    salary        NUMBER(8, 2),
    department_id NUMBER(8) ,
    CONSTRAINT emp_salary_min CHECK (salary >= 1.0),
    CONSTRAINT con_department_id FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE CASCADE
);

CREATE SEQUENCE employees_seq START WITH 1;

ALTER TABLE EMPLOYEES
    MODIFY employee_id DEFAULT employees_seq.NEXTVAL;

-- insert data into the EMPLOYEES table
BEGIN
    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
            'Steven'
           , 'King'
           , 'SKING'
           , '515.123.4567'
           , 24000
           , 100
           );
    --
    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Neena'
           , 'Kochhar'
           , 'NKOCHHAR'
           , '515.123.4568'
           , 17000
           , 110);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Lex'
           , 'De Haan'
           , 'LDEHAAN'
           , '515.123.4569'
           , 17000
           , 120);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Alexander'
           , 'Hunold'
           , 'AHUNOLD'
           , '590.423.4567'
           , 9000
           , 130);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Bruce'
           , 'Ernst'
           , 'BERNST'
           , '590.423.4568'
           , 6000
           , 140);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'David'
           , 'Austin'
           , 'DAUSTIN'
           , '590.423.4569'
           , 4800
           , 150);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Valli'
           , 'Pataballa'
           , 'VPATABAL'
           , '590.423.4560'
           , 4800
           , 160);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Diana'
           , 'Lorentz'
           , 'DLORENTZ'
           , '590.423.5567'
           , 4200
           , 100);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Nancy'
           , 'Greenberg'
           , 'NGREENBE'
           , '515.124.4569'
           , 12008
           , 110);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Daniel'
           , 'Faviet'
           , 'DFAVIET'
           , '515.124.4169'
           , 9000
           , 120);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'John'
           , 'Chen'
           , 'JCHEN'
           , '515.124.4269'
           , 8200
           , 130);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Ismael'
           , 'Sciarra'
           , 'ISCIARRA'
           , '515.124.4369'
           , 7700
           , 140);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Jose Manuel'
           , 'Urman'
           , 'JMURMAN'
           , '515.124.4469'
           , 7800
           , 150);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Luis'
           , 'Popp'
           , 'LPOPP'
           , '515.124.4567'
           , 6900
           , 160);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Den'
           , 'Raphaely'
           , 'DRAPHEAL'
           , '515.127.4561'
           , 11000
           , 100);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
            'Alexander'
           , 'Khoo'
           , 'AKHOO'
           , '515.127.4562'
           , 3100
           , 110);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Shelli'
           , 'Baida'
           , 'Berlin'
           , '515.127.4563'
           , 2900
           , 120);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Sigal'
           , 'Tobias'
           , 'STOBIAS'
           , '515.127.4564'
           , 2800
           , 130);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Guy'
           , 'Himuro'
           , 'GHIMURO'
           , '515.127.4565'
           , 2600
           , 140);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Karen'
           , 'Colmenares'
           , 'KCOLMENA'
           , '515.127.4566'
           , 2500
           , 150);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Matthew'
           , 'Weiss'
           , 'MWEISS'
           , '650.123.1234'
           , 8000
           , 160);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Adam'
           , 'Fripp'
           , 'AFRIPP'
           , '650.123.2234'
           , 8200
           , 100);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Payam'
           , 'Kaufling'
           , 'PKAUFLIN'
           , '650.123.3234'
           , 7900
           , 110);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Shanta'
           , 'Vollman'
           , 'SVOLLMAN'
           , '650.123.4234'
           , 6500
           , 120);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Kevin'
           , 'Mourgos'
           , 'KMOURGOS'
           , '650.123.5234'
           , 5800
           , 130);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Julia'
           , 'Nayer'
           , 'JNAYER'
           , '650.124.1214'
           , 3200
           , 140);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Irene'
           , 'Mikkilineni'
           , 'IMIKKILI'
           , '650.124.1224'
           , 2700
           , 150);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'James'
           , 'Landry'
           , 'JLANDRY'
           , '650.124.1334'
           , 2400
           , 160);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Steven'
           , 'Markle'
           , 'SMARKLE'
           , '650.124.1434'
           , 2200
           , 100);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Laura'
           , 'Bissot'
           , 'LBISSOT'
           , '650.124.5234'
           , 3300
           , 110);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'Mozhe'
           , 'Atkinson'
           , 'MATKINSO'
           , '650.124.6234'
           , 2800
           , 120);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'James'
           , 'Marlow'
           , 'JAMRLOW'
           , '650.124.7234'
           , 2500
           , 130);

    INSERT INTO employees (first_name, last_name, email, phone_number, salary, department_id)
    VALUES (
             'TJ'
           , 'Olson'
           , 'TJOLSON'
           , '650.124.8234'
           , 2100
           , 140);
END;
