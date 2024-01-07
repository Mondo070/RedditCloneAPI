package com.redditclone.reddit.user;

import java.time.LocalDateTime;

public record UserDto(
        long id,
        String username,
        String email,
        LocalDateTime dateJoined

) {
}
