create table IF NOT EXISTS files (
    id   serial primary key,
    name varchar,
    path varchar unique,
    file_post_id INT REFERENCES auto_post(id)
    );