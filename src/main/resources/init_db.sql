/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  justlowkee
 * Created: 1 Jun 2025
 */

CREATE TABLE orders (
    order_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
/* Table structure for table  'user account*/

/* Table structure for table 'role' */
CREATE TABLE role (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(50) UNIQUE NOT NULL
);

/* Insert default cafe roles (only if they don't exist) */
INSERT INTO role (name) SELECT 'ADMIN' FROM SYSIBM.SYSDUMMY1 WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ADMIN');
INSERT INTO role (name) SELECT 'MANAGER' FROM SYSIBM.SYSDUMMY1 WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'MANAGER');
INSERT INTO role (name) SELECT 'CASHIER' FROM SYSIBM.SYSDUMMY1 WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'CASHIER');
INSERT INTO role (name) SELECT 'BARISTA' FROM SYSIBM.SYSDUMMY1 WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'BARISTA');
INSERT INTO role (name) SELECT 'KITCHEN_STAFF' FROM SYSIBM.SYSDUMMY1 WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'KITCHEN_STAFF');
INSERT INTO role (name) SELECT 'WAITER' FROM SYSIBM.SYSDUMMY1 WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'WAITER');

CREATE TABLE user_account (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50),
    photo BLOB
);

