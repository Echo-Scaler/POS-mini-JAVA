CREATE DATABASE IF NOT EXISTS posdb;

USE posdb;

-- Drop table and recreate to ensure correct schema
DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    barcode VARCHAR(100) UNIQUE NOT NULL,
    category VARCHAR(100),
    price DOUBLE NOT NULL,
    quantity INT NOT NULL
);

-- Sample Data with Categories
INSERT INTO
    products (
        name,
        barcode,
        category,
        price,
        quantity
    )
VALUES (
        'Organic Milk',
        '1001',
        'Dairy',
        3.50,
        50
    ),
    (
        'Whole Wheat Bread',
        '1002',
        'Bakery',
        2.20,
        30
    ),
    (
        'Avocado',
        '1003',
        'Produce',
        1.50,
        100
    ),
    (
        'Greek Yogurt',
        '1004',
        'Dairy',
        4.00,
        40
    ),
    (
        'Artisanal Pizza',
        '1005',
        'Frozen',
        12.00,
        15
    ),
    (
        'Fresh Strawberries',
        '1006',
        'Produce',
        5.00,
        20
    ),
    (
        'Espresso Beans',
        '1007',
        'Beverages',
        18.50,
        10
    );