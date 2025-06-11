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

CREATE TABLE user_account (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50),
    photo BLOB
);

