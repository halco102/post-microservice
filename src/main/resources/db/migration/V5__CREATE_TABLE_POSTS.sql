create table if not exists posts(
id int8 DEFAULT nextval('post_sequence') primary key,
title varchar(50),
description varchar(200),
image_url varchar(200),
allow_comment boolean,
created_at DATE,
edited_at DATE,
users_id int8,

foreign key (users_id) references users(id)
);