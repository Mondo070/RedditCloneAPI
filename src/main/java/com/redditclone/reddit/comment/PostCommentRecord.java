package com.redditclone.reddit.comment;

import java.time.LocalDateTime;
import java.util.List;

public record PostCommentRecord(
        long id,
        long parentid,
        String text,
        LocalDateTime date_submitted
) {}
