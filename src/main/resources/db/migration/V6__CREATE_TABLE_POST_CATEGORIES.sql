create table if not exists posts_categories(
posts_id int8,
categories_id int8,

foreign key (posts_id) references posts(id),
foreign key (categories_id) references categories(id)
);