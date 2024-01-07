CREATE TABLE upvotes (
    id SERIAL PRIMARY KEY,
    date_time TIMESTAMP,
    status VARCHAR(255) NOT NULL,
    upvotable_type VARCHAR(255) NOT NULL,
    user_id BIGINT REFERENCES users(id),
    comment_id BIGINT REFERENCES comments(id),
    post_id BIGINT REFERENCES posts(id)
);