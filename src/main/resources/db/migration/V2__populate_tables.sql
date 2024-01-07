insert into users(username)
values ('Armando'),
       ('Armani'),
       ('Adrian');

insert into subreddits(name)
values ('NBA'),
       ('NFL'),
       ('MLB');

insert into posts(title, text, date_submitted, user_id, subreddit_id)
values ('Westbrook is the goat', 'Just watch the games bro', current_date, 103, 101),
       ('Chiefs are winning the SB', 'Back to back baby', current_date, 103, 102),
       ('Jets are gonna win it all', 'Aaron Rogers bro', current_date, 102, 102),
       ('Mookie Betts is the MVP', 'I dont actually watch baseball', current_date, 102, 103),
       ('I love the Knicks', 'This is our year', current_date, 101, 101),
       ('Mets suck', 'Fuck this', current_date, 101, 103);
