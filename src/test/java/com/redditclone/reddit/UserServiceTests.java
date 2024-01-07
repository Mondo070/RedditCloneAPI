package com.redditclone.reddit;

import com.redditclone.reddit.post.PostEntity;
import com.redditclone.reddit.subreddit.SubredditDto;
import com.redditclone.reddit.subreddit.SubredditEntity;
import com.redditclone.reddit.subreddit.SubredditRepository;
import com.redditclone.reddit.user.UserDto;
import com.redditclone.reddit.user.UserEntity;
import com.redditclone.reddit.user.UserRepository;
import com.redditclone.reddit.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubredditRepository subredditRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity user;

    @BeforeEach
    public void setup() {
        PostEntity post = Mockito.mock(PostEntity.class);
        SubredditEntity subreddit = Mockito.mock(SubredditEntity.class);

        user = UserEntity.builder()
                .id(1L)
                .username("Mondo3011")
                .email("mondo3011@gmail.com")
                .dateJoined(LocalDateTime.of(2023, Month.MAY, 30, 0, 0, 0))
                .posts(List.of(post))
                .subscribedSubreddits(Set.of(subreddit))
                .build();
    }

    @Test
    public void givenUserObject_whenCreateUser_thenReturnUserObject() {
        given(userRepository.save(user)).willReturn(user);

        UserEntity savedUser = userService.createUser(user);

        assertThat(savedUser).isNotNull();
    }

    @Test
    public void givenUserObject_whenEditEmail_thenReturnUpdatedUser() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);
        user.setEmail("updated@gmail.com");

        UserEntity updatedUser = userService.editEmail(user.getId(), "updated@gmail.com");

        assertThat(updatedUser.getEmail()).isEqualTo("updated@gmail.com");
    }

    @Test
    public void givenNonExistentUser_whenEditEmail_thenThrowsException(){
        long nonExistentUserId = 2L;

        given(userRepository.findById(nonExistentUserId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userService.editEmail(nonExistentUserId, "Test");
        });

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void givenUserId_whenDeleteUser_thenNothing(){
        willDoNothing().given(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void givenUserId_whenFindUserById_thenReturnUserDto() {
        given(userRepository.findUserDtoById(user.getId())).willReturn(mock(UserDto.class));

        UserDto userDto = userService.findUserById(user.getId());

        assertThat(userDto).isNotNull();
    }

    @Test
    public void givenUserId_whenFindUserById_thenNull() {
        given(userRepository.findUserDtoById(10L)).willReturn(null);

        UserDto userDto = userService.findUserById(10L);

        assertThat(userDto).isNull();
    }

    @Test
    public void givenUserId_whenFindAllSubredditSubsByUser_thenReturnSubList() {
        given(subredditRepository.findAllSubredditsByUserId(user.getId()))
                .willReturn(List.of(mock(SubredditDto.class), mock(SubredditDto.class)));

        List<SubredditDto> subList = userService.findAllSubredditSubsByUserId(user.getId());

        assertThat(subList).isNotNull();
        assertThat(subList.size()).isEqualTo(2);
    }

    @Test
    public void givenSubredditIdAndUserId_WhenAddSubredditSubToUser_thenAddSubredditToUser() {
        SubredditEntity subreddit = SubredditEntity.builder()
                .id(1L)
                .name("test")
                .dateCreated(LocalDateTime.now())
                .posts(List.of(mock(PostEntity.class)))
                .subscribers(Set.of(mock(UserEntity.class)))
                .build();

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(subredditRepository.findById(subreddit.getId())).willReturn(Optional.of(subreddit));

        userService.addSubredditSubToUser(user.getId(), subreddit.getId());

        verify(userRepository, times(1)).save(user);
        verify(subredditRepository, times(1)).save(subreddit);

        assertTrue(user.getSubscribedSubreddits().contains(subreddit));
    }

    @Test
    public void givenSubredditIdAndUserId_WhenRemoveSubredditSubToUser_thenRemoveSubredditFromUser() {
        SubredditEntity subreddit = SubredditEntity.builder()
                .id(1L)
                .name("test")
                .dateCreated(LocalDateTime.now())
                .posts(List.of(mock(PostEntity.class)))
                .subscribers(Set.of(mock(UserEntity.class)))
                .build();

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(subredditRepository.findById(subreddit.getId())).willReturn(Optional.of(subreddit));

        userService.removeSubredditSubFromUser(user.getId(), subreddit.getId());

        verify(userRepository, times(1)).save(user);
        verify(subredditRepository, times(1)).save(subreddit);

        assertFalse(user.getSubscribedSubreddits().contains(subreddit));
    }

}
