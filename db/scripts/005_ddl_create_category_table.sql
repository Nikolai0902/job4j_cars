create table category (
    id serial primary key,
    name varchar not null
);

INSERT INTO category (name) VALUES ('Легковые'), ('Коммерческие'), ('Специальные'), ('Мото');