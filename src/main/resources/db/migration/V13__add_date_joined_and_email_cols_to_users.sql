ALTER TABLE users ADD COLUMN date_joined timestamp DEFAULT current_date;
ALTER TABLE users ADD COLUMN email varchar(255) NOT NULL default 'default@gmail.com';
