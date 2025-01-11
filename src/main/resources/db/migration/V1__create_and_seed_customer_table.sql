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
    phone VARCHAR(15) NOT NULL,
    country SMALLINT NOT NULL,
    demonym VARCHAR(15) NOT NULL,
    disable BOOLEAN NOT NULL DEFAULT FALSE
);

-- Insertar múltiples registros si la tabla está vacía
INSERT INTO customer (customerid, firstname, middlename, lastname, secondlastname, email, address, phone, country, demonym, disable)
SELECT * FROM (
    SELECT 1 AS customerid, 'John' AS firstname, NULL AS middlename, 'Doe' AS lastname, NULL AS secondlastname,
           'john.doe@example.com' AS email, '123 Main St' AS address, '+123456789' AS phone, 840 AS country,
           'American' AS demonym, FALSE AS disable
    UNION ALL
    SELECT 2, 'Jane', 'A.', 'Smith', NULL, 'jane.smith@example.com', '456 Elm St', '+123456788', 840, 'American', FALSE
    UNION ALL
    SELECT 3, 'Carlos', NULL, 'Martinez', 'Gomez', 'carlos.martinez@example.com', '789 Oak St', '+123456787', 604, 'Canadian', FALSE
    UNION ALL
    SELECT 4, 'Maria', 'Elena', 'Garcia', NULL, 'maria.garcia@example.com', '101 Pine St', '+123456786', 484, 'Mexican', FALSE
    UNION ALL
    SELECT 5, 'Luis', NULL, 'Lopez', NULL, 'luis.lopez@example.com', '202 Birch St', '+123456785', 484, 'Mexican', FALSE
    UNION ALL
    SELECT 6, 'Anna', 'Maria', 'Gonzalez', 'Fernandez', 'anna.gonzalez@example.com', '303 Cedar St', '+123456784', 124, 'Canadian', FALSE
    UNION ALL
    SELECT 7, 'Michael', 'J.', 'Brown', NULL, 'michael.brown@example.com', '404 Maple St', '+123456783', 276, 'German', FALSE
    UNION ALL
    SELECT 8, 'Sofia', NULL, 'Martinez', NULL, 'sofia.martinez@example.com', '505 Cherry St', '+123456782', 380, 'Italian', FALSE
    UNION ALL
    SELECT 9, 'Liam', 'William', 'Wilson', 'Davis', 'liam.wilson@example.com', '606 Palm St', '+123456781', 36, 'Australian', FALSE
    UNION ALL
    SELECT 10, 'Emma', NULL, 'Johnson', NULL, 'emma.johnson@example.com', '707 Walnut St', '+123456780', 250, 'French', FALSE
    UNION ALL
    SELECT 11, 'Oliver', NULL, 'Anderson', NULL, 'oliver.anderson@example.com', '808 Oak St', '+123456779', 124, 'Canadian', FALSE
    UNION ALL
    SELECT 12, 'Lucas', NULL, 'Thomas', NULL, 'lucas.thomas@example.com', '909 Pine St', '+123456778', 826, 'British', FALSE
    UNION ALL
    SELECT 13, 'Ella', 'Grace', 'Harris', NULL, 'ella.harris@example.com', '100 Birch St', '+123456777', 36, 'Australian', FALSE
    UNION ALL
    SELECT 14, 'Benjamin', NULL, 'Walker', 'Smith', 'benjamin.walker@example.com', '200 Maple St', '+123456776', 840, 'American', FALSE
    UNION ALL
    SELECT 15, 'Isabella', NULL, 'King', NULL, 'isabella.king@example.com', '300 Cedar St', '+123456775', 76, 'Brazilian', FALSE
) AS temp_data
WHERE NOT EXISTS (SELECT 1 FROM customer);
