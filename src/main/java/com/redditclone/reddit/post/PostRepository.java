package com.redditclone.reddit.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("""
        select new com.redditclone.reddit.post.PostDto(
            p.id as id,
            p.title as title,
            p.text as text,
            p.upvotes as upvotes,
            p.dateSubmitted as dateSubmitted,
            u.username as username,
            s.name as name
        )
        from PostEntity p
        join p.user u
        join p.subreddit s
        order by p.dateSubmitted
        """)
    List<PostDto> findAllPostDtosByDateSubmittedAsc();
    @Query("""
        select new com.redditclone.reddit.post.PostDto(
            p.id as id,
            p.title as title,
            p.text as text,
            p.upvotes as upvotes,
            p.dateSubmitted as dateSubmitted,
            u.username as username,
            s.name as name
        )
        from PostEntity p
        join p.user u
        join p.subreddit s
        where p.id = :postId
    """)
    PostDto findPostDtoById(
                    @Param("postId")long postId
            );

    @Query("""
        select new com.redditclone.reddit.post.PostDto(
            p.id as id,
            p.title as title,
            p.text as text,
            p.upvotes as upvotes,
            p.dateSubmitted as dateSubmitted,
            u.username as username,
            s.name as name
        )
        from PostEntity p
        join p.user u
        join p.subreddit s
        where s.name = :subredditName
        order by p.dateSubmitted
        """)
    List<PostDto> findAllPostDtosBySubredditByDateSubmittedAsc(@Param("subredditName") String subredditName);

    @Query("""
        select new com.redditclone.reddit.post.PostDto(
            p.id as id,
            p.title as title,
            p.text as text,
            p.upvotes as upvotes,
            p.dateSubmitted as dateSubmitted,
            u.username as username,
            s.name as name
        )
        from PostEntity p
        join p.user u
        join p.subreddit s
        where u.username = :username
        order by p.dateSubmitted
        """)
    List<PostDto> findAllPostDtosByUsernameByDateSubmittedAsc(@Param("username") String username);

    @Query("""
        select new com.redditclone.reddit.post.PostDto(
            p.id as id,
            p.title as title,
            p.text as text,
            p.upvotes as upvotes,
            p.dateSubmitted as dateSubmitted,
            u.username as username,
            s.name as name
        )
        from PostEntity p
        join p.user u
        join p.subreddit s
        where s.name = :subredditName
        and p.dateSubmitted BETWEEN :lastHour AND :currentDate
        order by p.upvotes
        """)
    List<PostDto> findAllPostsBySubredditSortedByTopOfHour(
            @Param("subredditName") String subredditName,
            @Param("currentDate") LocalDateTime currentDate,
            @Param("lastHour") LocalDateTime lastHour);

    @Query("""
        select new com.redditclone.reddit.post.PostDto(
            p.id as id,
            p.title as title,
            p.text as text,
            p.upvotes as upvotes,
            p.dateSubmitted as dateSubmitted,
            u.username as username,
            s.name as name
        )
        from PostEntity p
        join p.user u
        join p.subreddit s
        where s.name = :subredditName
        and p.dateSubmitted BETWEEN :lastDay AND :currentDate
        order by p.upvotes
        """)
    List<PostDto> findAllPostsBySubredditSortedByTopOfDay(
            @Param("subredditName") String subredditName,
            @Param("currentDate") LocalDateTime currentDate,
            @Param("lastDay") LocalDateTime lastDay);

    @Query("""
        select new com.redditclone.reddit.post.PostDto(
            p.id as id,
            p.title as title,
            p.text as text,
            p.upvotes as upvotes,
            p.dateSubmitted as dateSubmitted,
            u.username as username,
            s.name as name
        )
        from PostEntity p
        join p.user u
        join p.subreddit s
        where s.name = :subredditName
        and p.dateSubmitted BETWEEN :lastWeek AND :currentDate
        order by p.upvotes
        """)
    List<PostDto> findAllPostsBySubredditSortedByTopOfWeek(
            @Param("subredditName") String subredditName,
            @Param("currentDate") LocalDateTime currentDate,
            @Param("lastWeek") LocalDateTime lastWeek);

    @Query("""
        select new com.redditclone.reddit.post.PostDto(
            p.id as id,
            p.title as title,
            p.text as text,
            p.upvotes as upvotes,
            p.dateSubmitted as dateSubmitted,
            u.username as username,
            s.name as name
        )
        from PostEntity p
        join p.user u
        join p.subreddit s
        where s.name = :subredditName
        and p.dateSubmitted BETWEEN :lastMonth AND :currentDate
        order by p.upvotes
        """)
    List<PostDto> findAllPostsBySubredditSortedByTopOfMonth(
            @Param("subredditName") String subredditName,
            @Param("currentDate") LocalDateTime currentDate,
            @Param("lastMonth") LocalDateTime lastMonth);

    @Query("""
        select new com.redditclone.reddit.post.PostDto(
            p.id as id,
            p.title as title,
            p.text as text,
            p.upvotes as upvotes,
            p.dateSubmitted as dateSubmitted,
            u.username as username,
            s.name as name
        )
        from PostEntity p
        join p.user u
        join p.subreddit s
        where s.name = :subredditName
        and p.dateSubmitted BETWEEN :lastYear AND :currentDate
        order by p.upvotes
        """)
    List<PostDto> findAllPostsBySubredditSortedByTopOfYear(
            @Param("subredditName") String subredditName,
            @Param("currentDate") LocalDateTime currentDate,
            @Param("lastYear") LocalDateTime lastYear);

    @Query("""
        select new com.redditclone.reddit.post.PostDto(
            p.id as id,
            p.title as title,
            p.text as text,
            p.upvotes as upvotes,
            p.dateSubmitted as dateSubmitted,
            u.username as username,
            s.name as name
        )
        from PostEntity p
        join p.user u
        join p.subreddit s
        where s.name = :subredditName
        order by p.upvotes
        """)
    List<PostDto> findAllPostsBySubredditSortedByTopOfAllTime(@Param("subredditName") String subredditName);

}
