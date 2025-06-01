/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  justlowkee
 * Created: 1 Jun 2025
 */

CREATE TABLE inventory (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    category VARCHAR(20) NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0),
    unit_price DECIMAL(8,2) NOT NULL CHECK (unit_price > 0),
    UNIQUE (name, category)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    item_id INT REFERENCES inventory(id),
    quantity INT NOT NULL,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_account (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    firstname VARCHAR(100),
    surname VARCHAR(100),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    role VARCHAR(50),
    photo BLOB
);


