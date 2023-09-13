create table history_owners (
   id serial PRIMARY KEY,
   car_id int REFERENCES car(id),
   owner_id int REFERENCES owners(id)
);
