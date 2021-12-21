CREATE TABLE IF NOT EXISTS product(
    id serial PRIMARY KEY ,
    name varchar(64) NOT NULL ,
    price numeric NOT NULL ,
    category varchar(64) NOT NULL ,
    start_date date NOT NULL ,
    size varchar(16)
);

CREATE TABLE IF NOT EXISTS person(
    id serial PRIMARY KEY ,
    login varchar(64) NOT NULL ,
    password varchar(64) NOT NULL ,
    email varchar(64) NOT NULL ,
    role varchar(32) NOT NULL ,
    blocked boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS ordering(
    id serial PRIMARY KEY ,
    product_id int references product(id) NOT NULL ,
    person_id int references person(id) NOT NULL ,
    status varchar(32) NOT NULL DEFAULT 'registered'
);
