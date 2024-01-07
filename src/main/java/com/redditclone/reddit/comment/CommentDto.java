package com.redditclone.reddit.comment;

import java.time.LocalDateTime;

public interface CommentDto {
    long getId();
    long getParentId();
    String getText();
    LocalDateTime getDateSubmitted();
    Integer getUpvotes();
    String getUsername();
}
