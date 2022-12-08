create table if not exists post_like_dislike(
users_id int8,
posts_id int8,
is_like boolean,

foreign key (users_id) references users(id),
foreign key (posts_id) references posts(id)
);