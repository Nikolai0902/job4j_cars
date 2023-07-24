create table engine (
    id serial primary key,
    name varchar not null
);

create table car (
    id serial primary key,
    name varchar not null,
    engine_id int not null references engine(id)
);

create table owners (
    id serial primary key,
    name varchar not null,
    user_id int not null references auto_user(id)
);

create table history_owners (
   id serial PRIMARY KEY,
   car_id int REFERENCES car(id),
   owner_id int REFERENCES owners(id)
);

create table history (
   id SERIAL PRIMARY KEY,
    startAt Timestamp,
    endAt Timestamp
);