
INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

-- id's: 3, 4, 5
INSERT INTO RESTAURANTS (name)
VALUES ('Bistro'),
       ('Japanese'),
       ('Georgian');

-- id's: 6, 7, 8, 9, 10
INSERT INTO MENUS (date, restaurant_id)
VALUES ('2022-04-28', 4),
       ('2022-04-28', 5),
       ('2022-04-28', 5),
       ('2022-05-01', 3),
       ('2022-05-01', 5);

INSERT INTO DISHES (name, price, menu_id)
VALUES ('Shawarma', 230, 6),
       ('Pita', 180, 6),
       ('Lemonade', 90, 6),
       ('Sushi', 320, 7),
       ('Ramen', 280, 7),
       ('Wok', 335, 7),
       ('Khinkali', 55, 8),
       ('Khachapuri', 230, 8),
       ('Lobio', 290, 8),
       ('Shawarma', 240, 9),
       ('Lemonade', 90, 9),
       ('Khachapuri', 300, 9),
       ('Khinkali', 60, 10),
       ('Lemonade', 70, 10),
       ('Khachapuri', 280, 10);

INSERT INTO VOTES (date, user_id, restaurant_id)
VALUES ('2022-04-28', 1, 3),
       ('2022-04-28', 2, 3),
       ('2022-05-01', 1, 5),
       ('2022-05-01', 2, 4);