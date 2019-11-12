DROP TABLE IF EXISTS test;
-- DROP TABLE IF EXISTS employees;
-- DROP TABLE IF EXISTS employees2;
-- DROP TABLE IF EXISTS Employee_Impl;

-- -- Create the EMPLOYEES table using a .sql
-- CREATE TABLE IF NOT EXISTS employees (
--   database_id INT AUTO_INCREMENT  PRIMARY KEY,
--   address VARCHAR(250) DEFAULT NULL,
--   first_name VARCHAR(250) NOT NULL,
--   last_name VARCHAR(250) NOT NULL,
--   employee_id VARCHAR(250)
-- );

-- -- Insert into EMPLOYEES table that is built by spring.
-- INSERT INTO employees (DATABASE_ID, address, first_name, last_name) VALUES
--     (1, '90 Address Rd', 'max', 'lill'),
--     (2, '80 Address Rd', 'lance', 'goodfellow'),
--     (3, '70 Address Rd', 'franco', 'loi');

-- -- Single insert into EMPLOYEES table that is built by spring.
-- INSERT INTO employees (DATABASE_ID, address, first_name, last_name, employee_id) VALUES
--     (4, '60 Address Rd', 'mansoor', 'Syed', '01');

-- -- Single insert into a spring generated EMPLOYEES table.
-- INSERT INTO employees (database_id, address, first_name, last_name, employee_id) VALUES (1,'60 Address Rd', 'mansoor', 'Syed','01');

-- -- Use this to populate a .sql generated table with auto increment
-- INSERT INTO employees (address, first_name, last_name) VALUES
--     ('90 Address Rd', 'max', 'lill'),
--     ('80 Address Rd', 'lance', 'goodfellow'),
--     ('70 Address Rd', 'franco', 'loi');
