DELETE FROM posts;

insert into posts(title, text, date_submitted, user_id, subreddit_id)
values ('Westbrook is the goat', 'Just watch the games bro', current_timestamp, 103, 101),
       ('Chiefs are winning the SB', 'Back to back baby', current_timestamp, 103, 102),
       ('Jets are gonna win it all', 'Aaron Rogers bro', current_timestamp, 102, 102),
       ('Mookie Betts is the MVP', 'I dont actually watch baseball', current_timestamp, 102, 103),
       ('I love the Knicks', 'This is our year', current_timestamp, 101, 101),
       ('Mets suck', 'Fuck this', current_timestamp, 101, 103);