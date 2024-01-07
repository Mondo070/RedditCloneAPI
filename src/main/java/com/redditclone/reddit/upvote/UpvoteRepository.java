package com.redditclone.reddit.upvote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UpvoteRepository extends JpaRepository<UpvoteEntity, Long> {

    @Query("""
    SELECT new com.redditclone.reddit.upvote.UpvoteDto(
        u.id,
        u.dateTime,
        u.user.id,
        CASE WHEN u.comment IS NOT NULL THEN u.comment.id ELSE u.post.id END,
        CASE WHEN u.comment IS NOT NULL THEN 'COMMENT' ELSE 'POST' END,
        u.status
    )
    FROM UpvoteEntity u
    WHERE (:commentId IS NULL OR u.comment.id = :commentId)
        AND (:postId IS NULL OR u.post.id = :postId)
            """)
    UpvoteDto findUpvoteDtoByUpvotableId(
            @Param("commentId") Long commentId,
            @Param("postId") Long postId
    );

}
