CREATE TABLE IF NOT EXISTS departments
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR2(30) UNIQUE NOT NULL,
    location VARCHAR2(35) NOT NULL
);

CREATE TABLE IF NOT EXISTS employees
(
    employee_id   INT AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR2(20)        NOT NULL,
    last_name     VARCHAR2(25)        NOT NULL,
    email         VARCHAR2(25) UNIQUE NOT NULL,
    phone_number  VARCHAR2(20) UNIQUE NOT NULL,
    salary        NUMBER(8, 2),
    department_id NUMBER(8) ,
    CONSTRAINT emp_salary_min CHECK (salary >= 1.0),
    CONSTRAINT con_department_id FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE CASCADE
);