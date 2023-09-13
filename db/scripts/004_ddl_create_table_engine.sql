create table engine (
    id serial primary key,
    name varchar not null
);

INSERT INTO engine (name) VALUES ('Бензин'), ('Дизель'), ('Гибрид'), ('Электро');
