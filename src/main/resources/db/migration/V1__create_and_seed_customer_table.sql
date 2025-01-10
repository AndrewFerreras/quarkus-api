-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS db_customers;

-- Usar la base de datos
USE db_customers;

-- Crear la tabla 'customer' con el campo 'disable'
CREATE TABLE IF NOT EXISTS customer (
    customerid INT NOT NULL PRIMARY KEY,
    firstname VARCHAR(15) NOT NULL,
    middlename VARCHAR(15) DEFAULT NULL,
    lastname VARCHAR(15) NOT NULL,
    secondlastname VARCHAR(15) DEFAULT NULL,
    email VARCHAR(35) NOT NULL,
    address VARCHAR(180) NOT NULL,
    phone VARCHAR(25) NOT NULL,
    country SMALLINT NOT NULL,
    demonym VARCHAR(25) NOT NULL,
    disable BOOLEAN NOT NULL DEFAULT FALSE
);

-- Verificar si la tabla 'customer' está vacía antes de insertar registros
INSERT INTO customer (customerid, firstname, middlename, lastname, secondlastname, email, address, phone, country, demonym, disable)
SELECT customerid, firstname, middlename, lastname, secondlastname, email, address, phone, country, demonym, disable
FROM (
    VALUES
        (1, 'John', NULL, 'Doe', NULL, 'john.doe@example.com', '123 Main St', '+123456789', 840, 'American', FALSE),
        (2, 'Jane', 'A.', 'Smith', NULL, 'jane.smith@example.com', '456 Elm St', '+123456788', 840, 'American', FALSE),
        (3, 'Carlos', NULL, 'Martinez', 'Gomez', 'carlos.martinez@example.com', '789 Oak St', '+123456787', 604, 'Canadian', FALSE),
        (4, 'Maria', 'Elena', 'Garcia', NULL, 'maria.garcia@example.com', '101 Pine St', '+123456786', 484, 'Mexican', FALSE),
        (5, 'Luis', NULL, 'Lopez', NULL, 'luis.lopez@example.com', '202 Birch St', '+123456785', 484, 'Mexican', FALSE),
        (6, 'Anna', 'Maria', 'Gonzalez', 'Fernandez', 'anna.gonzalez@example.com', '303 Cedar St', '+123456784', 124, 'Canadian', FALSE),
        (7, 'Michael', 'J.', 'Brown', NULL, 'michael.brown@example.com', '404 Maple St', '+123456783', 276, 'German', FALSE),
        (8, 'Sofia', NULL, 'Martinez', NULL, 'sofia.martinez@example.com', '505 Cherry St', '+123456782', 380, 'Italian', FALSE),
        (9, 'Liam', 'William', 'Wilson', 'Davis', 'liam.wilson@example.com', '606 Palm St', '+123456781', 36, 'Australian', FALSE),
        (10, 'Emma', NULL, 'Johnson', NULL, 'emma.johnson@example.com', '707 Walnut St', '+123456780', 250, 'French', FALSE),
        (11, 'Oliver', NULL, 'Anderson', NULL, 'oliver.anderson@example.com', '808 Oak St', '+123456779', 124, 'Canadian', FALSE),
        (12, 'Lucas', NULL, 'Thomas', NULL, 'lucas.thomas@example.com', '909 Pine St', '+123456778', 826, 'British', FALSE),
        (13, 'Ella', 'Grace', 'Harris', NULL, 'ella.harris@example.com', '100 Birch St', '+123456777', 36, 'Australian', FALSE),
        (14, 'Benjamin', NULL, 'Walker', 'Smith', 'benjamin.walker@example.com', '200 Maple St', '+123456776', 840, 'American', FALSE),
        (15, 'Isabella', NULL, 'King', NULL, 'isabella.king@example.com', '300 Cedar St', '+123456775', 76, 'Brazilian', FALSE)
) AS data (customerid, firstname, middlename, lastname, secondlastname, email, address, phone, country, demonym, disable)
WHERE NOT EXISTS (SELECT 1 FROM customer);
