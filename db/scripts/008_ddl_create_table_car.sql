create table car (
    id serial primary key,
    name varchar not null,
    model varchar not null,
    category_id int not null references category(id),
    body_id int not null references body(id),
    engine_id int not null references engine(id)
);