package com.redditclone.reddit.upvote;

import java.time.LocalDateTime;

public record UpvoteDto(
        Long id,
        LocalDateTime dateTime,
        Long userid,
        Long commentId,
        Long postId,
        String status,
        String upvotableType
) {
}
