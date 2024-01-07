package com.redditclone.reddit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("""
        select new com.redditclone.reddit.user.UserDto(
            u.id,
            u.username,
            u.email,
            u.dateJoined
        )
        from UserEntity u
        where u.id = :userId
    """)
    UserDto findUserDtoById(
            @Param("userId")long userId
    );

    @Query("""
        select new com.redditclone.reddit.user.UserDto(
            u.id,
            u.username,
            u.email,
            u.dateJoined
        )
        from UserEntity u
        where u.username = :username
    """)
    UserDto findUserDtoByUsername(
            @Param("username") String username
    );

}
