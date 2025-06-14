/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  justlowkee
 * Created: 1 Jun 2025
 */


CREATE TABLE role (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE user_account (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50),
    photo BLOB
);

-- Insert default roles
INSERT INTO role (name) VALUES ('Administrator');
INSERT INTO role (name) VALUES ('Manager');
INSERT INTO role (name) VALUES ('Barista');
INSERT INTO role (name) VALUES ('Cashier');

-- Insert default admin user
INSERT INTO user_account (firstname, lastname, username, password, role) 
VALUES ('Admin', 'User', 'admin', 'admin123', 'Administrator');

INSERT INTO user_account (firstname, lastname, username, password, role) 
VALUES ('Lorenz', 'Soriano', 'enz', 'whoyou123', 'Barista');

-- Create menu items table
CREATE TABLE menu_items (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(500),
    price DECIMAL(8,2) NOT NULL CHECK (price > 0),
    category VARCHAR(50) NOT NULL,
    available BOOLEAN DEFAULT TRUE,
    image_url VARCHAR(200)
);

-- Create payments table
CREATE TABLE payments (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    order_id BIGINT,
    amount DECIMAL(10,2) NOT NULL CHECK (amount >= 0),
    payment_method VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transaction_id VARCHAR(100),
    change_amount DECIMAL(10,2) DEFAULT 0
);

-- Insert sample menu items
INSERT INTO menu_items (name, description, price, category, available) VALUES
('Espresso', 'Strong black coffee shot', 3.50, 'Coffee', true),
('Latte', 'Espresso with steamed milk', 4.50, 'Coffee', true),
('Cappuccino', 'Espresso with steamed milk foam', 4.75, 'Coffee', true),
('Americano', 'Espresso with hot water', 3.75, 'Coffee', true),
('Croissant', 'Buttery French pastry', 3.25, 'Pastry', true),
('Blueberry Muffin', 'Fresh blueberry muffin', 2.75, 'Pastry', true),
('Caesar Salad', 'Fresh romaine with caesar dressing', 8.50, 'Food', true),
('Grilled Chicken Sandwich', 'Grilled chicken with vegetables', 9.75, 'Food', true),
('Iced Coffee', 'Cold brewed coffee with ice', 4.25, 'Coffee', true),
('Flat White', 'Double shot espresso with steamed milk', 4.50, 'Coffee', true),
('Almond Croissant', 'Flaky pastry filled with almond cream', 3.75, 'Pastry', true);

