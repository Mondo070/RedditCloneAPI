package com.redditclone.reddit.upvote;

import java.time.LocalDateTime;

public interface Upvotable {
    Integer getUpvotes();
    LocalDateTime getDateSubmitted();
}