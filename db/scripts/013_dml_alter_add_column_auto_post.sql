ALTER TABLE auto_post ADD COLUMN IF NOT EXISTS car_id int REFERENCES car(id);
ALTER TABLE auto_post ADD COLUMN IF NOT EXISTS sold boolean not null;
ALTER TABLE auto_post ADD COLUMN IF NOT EXISTS price int not null;
ALTER TABLE auto_post ADD COLUMN IF NOT EXISTS mileage int not null;