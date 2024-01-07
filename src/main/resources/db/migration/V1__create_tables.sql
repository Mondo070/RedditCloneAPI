create table users
(
    id             bigserial not null,
    username       varchar   not null,
    primary key (id)
);

create table subreddits
(
    id      bigserial       not null,
    name   varchar         not null,
    primary key (id)
);

create table posts
(
    id              bigserial            not null,
    title           varchar              not null,
    text            varchar              not null,
    date_submitted  timestamp            not null,
    user_id         bigserial            not null
        references users(id),
    subreddit_id    bigserial            not null
        references subreddits(id),
    primary key (id)
);

create table comments
(
    id             bigserial                    not null,
    text           varchar                      not null,
    date_submitted timestamp,
    post_id        bigint references posts (id) not null,
    primary key (id)
);

ALTER SEQUENCE users_id_seq RESTART WITH 101;
ALTER SEQUENCE posts_id_seq RESTART WITH 101;
ALTER SEQUENCE comments_id_seq RESTART WITH 101;
ALTER SEQUENCE subreddits_id_seq RESTART WITH 101;

