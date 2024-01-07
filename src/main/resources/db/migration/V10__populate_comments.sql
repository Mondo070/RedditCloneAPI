insert into comments(text, date_submitted, post_id, parentid)
values ('comment 1', current_date, 123, null),
       ('reply 1 to comment 1', current_date, 123, 101),
       ('reply 1 to reply 1 of comment 1', current_date, 123, 102),
       ('reply 2 to reply 1 of comment 1', current_date, 123, 102),
       ('reply 2 to comment 1', current_date, 123, 101),
       ('comment 2', current_date, 123, null),
       ('reply to comment 2', current_date, 123, 105);