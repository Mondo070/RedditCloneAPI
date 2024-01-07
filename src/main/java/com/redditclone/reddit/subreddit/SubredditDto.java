package com.redditclone.reddit.subreddit;

import java.time.LocalDateTime;

public record SubredditDto(
        long id,
        String name,
        LocalDateTime date_created,
        int memberCount
) {
}
