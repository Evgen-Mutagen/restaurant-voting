DELETE
FROM user_roles;
DELETE
FROM votes;
DELETE
FROM users;
DELETE
FROM dishes;
DELETE
FROM menus;
DELETE
FROM restaurants;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

-- id's: 100002, 100003, 100004
INSERT INTO restaurants (name)
VALUES ('Bistro'),
       ('Japanese'),
       ('Georgian');

-- id's: 100005, 100006, 100007, 100008, 100009
INSERT INTO menus (date, restaurant_id)
VALUES ('2022-04-28', 100002),
       ('2022-04-28', 100003),
       ('2022-04-28', 100004),
       ('2022-05-01', 100002),
       ('2022-05-01', 100004);

INSERT INTO dishes (name, price, menu_id)
VALUES ('Shawarma', 230, 100005),
       ('Pita', 180, 100005),
       ('Lemonade', 90, 100005),
       ('Sushi', 320, 100006),
       ('Ramen', 280, 100006),
       ('Wok', 335, 100006),
       ('Khinkali', 55, 100007),
       ('Khachapuri', 230, 100007),
       ('Lobio', 290, 100007),
       ('Shawarma', 240, 100008),
       ('Lemonade', 90, 100008),
       ('Khachapuri', 300, 100008),
       ('Khinkali', 60, 100009),
       ('Lemonade', 70, 100009),
       ('Khachapuri', 280, 100009);

INSERT INTO votes (date, user_id, restaurant_id)
VALUES ('2022-04-28', 100000, 100002),
       ('2022-04-28', 100001, 100002),
       ('2022-05-01', 100000, 100004),
       ('2022-05-01', 100001, 100003);