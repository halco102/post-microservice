create table if not exists users(
id int8 primary key,
username varchar(50) unique,
email varchar(50) unique,
image_url varchar(200)
);