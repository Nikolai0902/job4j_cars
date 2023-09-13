CREATE TABLE if not exists participates (
   id serial PRIMARY KEY,
   auto_post_id int not null references auto_post(id),
   auto_user_id int not null references auto_user(id)
);