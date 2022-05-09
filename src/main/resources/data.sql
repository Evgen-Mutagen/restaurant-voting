INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

-- id's: 100002, 100003, 100004
INSERT INTO RESTAURANTS (name)
VALUES ('Bistro'),
       ('Japanese'),
       ('Georgian');

INSERT INTO DISHES (name, price)
VALUES ('Shawarma', 230),   -- 100005
       ('Pita', 180),       -- 100006
       ('Lemonade', 90),    -- 100007
       ('Sushi', 320),      -- 100008
       ('Ramen', 280),      -- 100009
       ('Wok', 335),        -- 100010
       ('khinkali', 280),   -- 100011
       ('Khachapuri', 230), -- 100012
       ('Lobio', 290),      -- 100013
       ('Ramen', 310),      -- 100014
       ('Khachapuri', 250); -- 100015

INSERT INTO MENUS (date, restaurant_id, dish_id)
VALUES ('2022-04-28', 100002, 100005),
       ('2022-04-28', 100002, 100006),
       ('2022-04-28', 100002, 100007),
       ('2022-04-28', 100003, 100008),
       ('2022-04-28', 100003, 100009),
       ('2022-04-28', 100003, 100010),
       ('2022-04-28', 100004, 100011),
       ('2022-04-28', 100004, 100012),
       ('2022-04-28', 100004, 100013),
       ('2022-05-01', 100002, 100006),
       ('2022-05-01', 100002, 100009),
       ('2022-05-01', 100002, 100011),
       ('2022-05-01', 100004, 100015),
       ('2022-05-01', 100004, 100009),
       ('2022-05-01', 100004, 100014);

INSERT INTO VOTES (date, user_id, restaurant_id)
VALUES ('2022-04-28', 100000, 100002),
       ('2022-04-28', 100001, 100002),
       ('2022-05-01', 100000, 100004),
       ('2022-05-01', 100001, 100003);