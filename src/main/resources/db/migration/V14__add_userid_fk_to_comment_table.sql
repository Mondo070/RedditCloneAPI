alter table comments
add column user_id BIGINT,
add constraint fk_comment_user
foreign key (user_id) references users(id);
update comments
set user_id = 101;
