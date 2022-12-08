create table if not exists categories(
id int8 DEFAULT nextval('categories_sequence') primary key,
name varchar(30) unique,
image_url varchar(200)
);