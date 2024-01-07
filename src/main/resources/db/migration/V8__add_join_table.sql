create table subreddit_subscriptions
(
    user_id         bigserial            not null
        references users(id),
    subreddit_id    bigserial            not null
        references subreddits(id),
    primary key (user_id, subreddit_id)
);