
CREATE DATABASE shopdb;
WITH ENCODING 'UTF8'
LC_COLLATE='ru_RU.UTF-8'
LC_CTYPE='ru_RU.UTF-8'
TEMPLATE template0;
\connect shopdb;

-- Создание таблицы Роли
CREATE TABLE roles (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

-- Создание таблицы Пользователи
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role_id INT,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE SET NULL
);

-- Создание таблицы Покупатели
CREATE TABLE buyers (
    user_id INT PRIMARY KEY,
    buyer_address VARCHAR(255) NOT NULL,
    buyer_phone VARCHAR(20) NOT NULL,
    CONSTRAINT fk_buyers_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Создание таблицы Товары
CREATE TABLE items (
    item_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    count INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);

-- Создание таблицы Заказы
CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Создание таблицы Заказ-Товар (связь M:N)
CREATE TABLE order_items (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    item_id INT NOT NULL,
    item_count INT NOT NULL,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_item FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE
);

-- Заполнение таблицы Роли
INSERT INTO roles (role_name) VALUES 
('Admin'),
('User'),
('Manager');

-- Заполнение таблицы Пользователи
INSERT INTO users (username, password, role_id) VALUES 
('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 1),
('user', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 2),
('manager', '6ee4a469cd4e91053847f5d3fcb61dbcc91e8f0ef10be7748da4c4a1ba382d17', 3),
('user1', '0a041b9462caa4a31bac3567e0b6e6fd9100787db2ab433d96f6d178cabfce90', 2);

-- Заполнение таблицы Покупатели
INSERT INTO buyers (user_id, buyer_address, buyer_phone) VALUES 
(2, 'ул. Ленина, 10', '+7 900 123-45-67'),
(4, 'ул. Пупкина, 12', '+7 12 123-45-67');

-- Заполнение таблицы Товары
INSERT INTO items (name, count, price) VALUES 
('Ноутбук Dell', 10, 750.00),
('Смартфон Samsung', 15, 500.00),
('Наушники Sony', 20, 70.00),
('Клавиатура Logitech', 25, 450.00);

-- Заполнение таблицы Заказы
INSERT INTO orders (user_id) VALUES 
(2),
(4);

-- Заполнение таблицы Заказ-Товар
INSERT INTO order_items (order_id, item_id, item_count) VALUES 
(1, 1, 1),
(1, 3, 2),
(2, 2, 1),
(2, 4, 1);
