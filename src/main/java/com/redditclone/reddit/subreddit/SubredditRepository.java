package com.redditclone.reddit.subreddit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubredditRepository extends JpaRepository<SubredditEntity, Long> {

    @Query("""
        select new com.redditclone.reddit.subreddit.SubredditDto(
            sub.id as id,
            sub.name as name
        ) 
        from SubredditEntity sub
        inner join sub.subscribers subscr
        where subscr.id = ?1 
    """)
    List<SubredditDto> findAllSubredditsByUserId(long userId);

    @Query("""
        select new com.redditclone.reddit.subreddit.SubredditDto(
            s.id,
            s.name,
            s.dateCreated,
            SIZE(s.subscribers) as memberCount
        )
        from SubredditEntity s
        where s.id = :subredditId
    """)
    SubredditDto findSubredditDtoById(
            @Param("subredditId")long subredditId
    );

    @Query("""
        select new com.redditclone.reddit.subreddit.SubredditDto(
            s.id,
            s.name,
            s.dateCreated,
            SIZE(s.subscribers) as memberCount
        )
        from SubredditEntity s
        where s.name = :subredditName
    """)
    SubredditDto findSubredditByName(
            @Param("subredditName") String name
    );

    @Query("""
    select new com.redditclone.reddit.subreddit.SubredditDto(
        s.id,
        s.name,
        s.dateCreated,
        SIZE(s.subscribers) as memberCount
    )
    from SubredditEntity s
    where LOWER(s.name) like LOWER(:prefix) || '%'
""")
    List<SubredditDto> findSubredditsByPrefix(@Param("prefix") String prefix);

}
