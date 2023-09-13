create table IF NOT EXISTS files (
    id   serial primary key,
    name varchar,
    path varchar unique,
    auto_post_id int REFERENCES auto_post (id)
    );