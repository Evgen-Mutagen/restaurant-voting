DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS restaurants;

CREATE SEQUENCE GLOBAL_SEQ START WITH 100000;

CREATE TABLE users
(
    id         INT       default GLOBAL_SEQ.nextval NOT NULL,
    name       VARCHAR(255)                         NOT NULL,
    email      VARCHAR(255)                         NOT NULL,
    password   VARCHAR(255)                         NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE               NOT NULL,
    registered TIMESTAMP DEFAULT NOW()              NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER      NOT NULL,
    role    VARCHAR(255) NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id   INTEGER default GLOBAL_SEQ.nextval PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name);

CREATE TABLE dishes
(
    id    INTEGER default GLOBAL_SEQ.nextval PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    price INTEGER      NOT NULL
);

CREATE TABLE menus
(
    id            INTEGER default GLOBAL_SEQ.nextval PRIMARY KEY,
    date          DATE    DEFAULT now() NOT NULL,
    restaurant_id INTEGER               NOT NULL,
    dish_id       INTEGER               NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (dish_id) REFERENCES dishes (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menu_date_restaurant_dish_idx ON menus (date, restaurant_id, dish_id);

CREATE TABLE votes
(
    id            INTEGER default GLOBAL_SEQ.nextval PRIMARY KEY,
    date          DATE    DEFAULT now() NOT NULL,
    user_id       INTEGER               NOT NULL,
    restaurant_id INTEGER               NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_date_user_idx ON votes (date, user_id);