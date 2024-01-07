package com.redditclone.reddit;


import com.redditclone.reddit.post.PostEntity;
import com.redditclone.reddit.subreddit.SubredditDto;
import com.redditclone.reddit.subreddit.SubredditEntity;
import com.redditclone.reddit.subreddit.SubredditRepository;
import com.redditclone.reddit.subreddit.SubredditService;
import com.redditclone.reddit.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class SubredditServiceTests {

    @Mock
    private SubredditRepository subredditRepository;

    @InjectMocks
    private SubredditService subredditService;

    private SubredditEntity subreddit;

    @BeforeEach
    public void setup() {
        subreddit = SubredditEntity.builder()
                .id(1L)
                .name("test")
                .dateCreated(LocalDateTime.now())
                .posts(List.of(mock(PostEntity.class)))
                .subscribers(Set.of(mock(UserEntity.class)))
                .build();
    }

    @Test
    public void givenSubredditObject_whenCreateSubreddit_thenReturnSubredditObject() {
        given(subredditRepository.save(subreddit)).willReturn(subreddit);

        SubredditEntity savedSubreddit = subredditService.createSubreddit(subreddit);

        assertThat(savedSubreddit).isNotNull();
    }

    @Test
    public void givenSubredditId_whenDeleteSubreddit_thenNothing(){
        willDoNothing().given(subredditRepository).deleteById(1L);

        subredditService.deleteSubreddit(1L);

        verify(subredditRepository, times(1)).deleteById(1L);
    }

    @Test
    public void givenSubredditId_whenFindSubredditById_thenReturnSubredditDto() {
        given(subredditService.findSubredditById(subreddit.getId())).willReturn(mock(SubredditDto.class));

        SubredditDto subredditDto = subredditService.findSubredditById(subreddit.getId());

        assertThat(subredditDto).isNotNull();
    }

    @Test
    public void givenPrefixString_whenFindSubredditByPrefix_thenReturnSubredditList() {
        given(subredditService.findSubredditsByPrefix("test"))
                .willReturn(List.of(mock(SubredditDto.class),mock(SubredditDto.class)));

        List<SubredditDto> subredditList = subredditService.findSubredditsByPrefix("test");

        assertThat(subredditList).isNotNull();
        assertThat(subredditList.size()).isEqualTo(2);
    }

}
