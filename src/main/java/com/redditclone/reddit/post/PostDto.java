package com.redditclone.reddit.post;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PostDto(
   long id,
   String title,
   String text,
   Integer upvotes,
   LocalDateTime dateSubmitted,
   String username,
   String subredditName
) {
}
