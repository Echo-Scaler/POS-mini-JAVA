CREATE DATABASE IF NOT EXISTS posdb;

USE posdb;

CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    barcode VARCHAR(100) UNIQUE NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL
);

-- Sample Data
INSERT INTO
    products (
        name,
        barcode,
        price,
        quantity
    )
VALUES (
        'Organic Milk',
        '1001',
        3.50,
        50
    ),
    (
        'Whole Wheat Bread',
        '1002',
        2.20,
        30
    ),
    ('Avocado', '1003', 1.50, 100),
    (
        'Greek Yogurt',
        '1004',
        4.00,
        40
    ),
    (
        'Artisanal Pizza',
        '1005',
        12.00,
        15
    );