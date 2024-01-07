package com.redditclone.reddit.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("""
        select new com.redditclone.reddit.comment.CommentDto(
            c.id,
            c.parent.id,
            c.text,
            c.dateSubmitted,
            c.upvotes,
            u.username
        )
        from CommentEntity c
        join c.user u
        where c.id = :commentId
    """)
    CommentDto findCommentDtoById(
            @Param("commentId") long commentId
    );

    @Query("""
        select new com.redditclone.reddit.comment.CommentDto(
            c.id,
            c.parent.id,
            c.text,
            c.dateSubmitted,
            c.upvotes,
            u.username
        )
        from CommentEntity c
        join c.user u
        where u.id = :userId
    """)
    List<CommentDto> findCommentsByUserID(
            @Param("userId") long userId
    );

    @Query(nativeQuery = true, value = """
            with recursive CommentThread as (
                select
                    c.id,
                    c.parentid,
                    c.text,
                    c.date_submitted,
                    c.upvotes,
                    u.username
                from
                    comments c
                join
                    users u on c.user_id = u.id
                where
                    parentid is null
                    and post_id = :postId
            
                union all
            
                select
                    c.id,
                    c.parentid,
                    c.text,
                    c.date_submitted,
                    c.upvotes,
                    u.username
                from
                    comments c
                join
                    users u on c.user_id = u.id
                join
                    CommentThread ct on c.parentid = ct.id
            )
            select *
            from
                CommentThread c
            order by
                id;
            """)
    List<CommentDto> getCommentsAndReplies(@Param("postId") long postId);

    @Query(nativeQuery = true, value = """
            with recursive CommentHierarchy as (
                select
                    c.id,
                    c.parentid,
                    c.text,
                    c.date_submitted,
                    c.upvotes,
                    u.username
                from
                    comments c
                join
                    users u on c.user_id = u.id
                where
                    c.id = :startId
          
                union all
               
                select
                    c.id,
                    c.parentid,
                    c.text,
                    c.date_submitted,
                    c.upvotes,
                    u.username
                from
                    comments c
                join
                    users u on c.user_id = u.id
                join
                    CommentHierarchy ch on c.parentid = ch.id
            )
            select *
            from
                CommentHierarchy;
            """)
    List<CommentDto> getCommentChainByParentId(@Param("startId") long startId);

}
