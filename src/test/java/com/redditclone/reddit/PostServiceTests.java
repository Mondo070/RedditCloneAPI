package com.redditclone.reddit;

import com.redditclone.reddit.comment.CommentEntity;
import com.redditclone.reddit.post.PostDto;
import com.redditclone.reddit.post.PostEntity;
import com.redditclone.reddit.post.PostRepository;
import com.redditclone.reddit.post.PostService;
import com.redditclone.reddit.subreddit.SubredditEntity;
import com.redditclone.reddit.user.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private PostEntity post;

    //setup mock post object for testing
    @BeforeEach
    public void setup() {
        CommentEntity comment = Mockito.mock(CommentEntity.class);
        UserEntity user = Mockito.mock(UserEntity.class);
        SubredditEntity sub = Mockito.mock(SubredditEntity.class);

        post = PostEntity.builder()
                .id((long)1)
                .title("Post")
                .text("text post")
                .upvotes(1)
                .dateSubmitted(LocalDateTime.of(2023, Month.MAY, 30, 12, 35))
                .comments(List.of(comment))
                .user(user)
                .subreddit(sub)
                .build();
    }

    //test for createPost method
    @Test
    public void givenPostObject_whenCreatePost_thenReturnPostObject() {
        given(postRepository.save(post)).willReturn(post);

        PostEntity savedPost = postService.createPost(post);

        assertThat(savedPost).isNotNull();
    }

    //test for updatePost method
    @Test
    public void givenPostObject_whenUpdatePost_thenReturnUpdatedPost(){
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(postRepository.save(post)).willReturn(post);
        post.setText("Updated text");

        PostEntity updatedPost = postService.updatePost(post.getId(), "Updated text");

        assertThat(updatedPost.getText()).isEqualTo("Updated text");
    }

    //test for updatePost method
    @Test
    public void givenNonExistentPost_whenUpdatePost_thenThrowsException(){
        long nonExistentPostId = 2L;

        given(postRepository.findById(nonExistentPostId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            postService.updatePost(nonExistentPostId, "Test");
        });

        verify(postRepository, never()).save(any(PostEntity.class));
    }

     //test for deletePost method
    @Test
    public void givenPostId_whenDeletePost_thenNothing(){
        willDoNothing().given(postRepository).deleteById(1L);

        postService.deletePost(1L);

        verify(postRepository, times(1)).deleteById(1L);
    }

    @Test
    public void givenPostId_whenFindPostById_thenReturnPostDto() {
        given(postRepository.findPostDtoById(post.getId())).willReturn(mock(PostDto.class));

        PostDto postDto = postService.getPostById(post.getId());

        assertThat(postDto).isNotNull();
    }

    @Test
    public void givenPostId_whenFindPostById_thenReturnNull() {
        given(postRepository.findPostDtoById(10L)).willReturn(null);

        PostDto postDto = postService.getPostById(10L);

        assertThat(postDto).isNull();
    }

    //test for findAllPostDtosByDateSubmittedAsc method
    @Test
    public void givenPostList_whenFindAllPostsByNewAsc_thenReturnPostList() {
        given(postRepository.findAllPostDtosByDateSubmittedAsc())
                .willReturn(List.of(mock(PostDto.class),mock(PostDto.class)));

        List<PostDto> postList = postService.findAllPostsSortedByNew();

        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(2);
    }

    //test for findAllPostsBySubredditSortedByByNew
    @Test
    public void givenSubredditName_whenFindAllPostsBySubredditByNew_thenReturnPostList() {
        given(postRepository.findAllPostDtosBySubredditByDateSubmittedAsc("subredditName"))
                .willReturn(List.of(mock(PostDto.class), mock(PostDto.class)));

        List<PostDto> postList = postService.findAllPostsBySubredditSortedByNew("subredditName");

        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(2);
    }

    //test for findAllPostsBySubredditSortedByTopOfHour
    @Test
    public void givenSubredditName_whenFindAllPostsBySubredditByTopOfHour_thenReturnPostList() {
        given(postRepository.findAllPostsBySubredditSortedByTopOfHour(eq("subredditName"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(List.of(mock(PostDto.class), mock(PostDto.class)));

        List<PostDto> postList = postService.findAllPostsBySubredditSortedByTopOfHour("subredditName");

        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(2);
    }

    //test for findAllPostsBySubredditSortedByTopOfDay
    @Test
    public void givenSubredditName_whenFindAllPostsBySubredditByTopOfDay_thenReturnPostList() {
        given(postRepository.findAllPostsBySubredditSortedByTopOfDay(eq("subredditName"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(List.of(mock(PostDto.class), mock(PostDto.class)));

        List<PostDto> postList = postService.findAllPostsBySubredditSortedByTopOfDay("subredditName");

        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(2);
    }

    //test for findAllPostsBySubredditSortedByTopOfWeek
    @Test
    public void givenSubredditName_whenFindAllPostsBySubredditByTopOfWeek_thenReturnPostList() {
       given(postRepository.findAllPostsBySubredditSortedByTopOfWeek(eq("subredditName"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(List.of(mock(PostDto.class), mock(PostDto.class)));

        List<PostDto> postList = postService.findAllPostsBySubredditSortedByTopOfWeek("subredditName");

        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(2);
    }

    //test for findAllPostsBySubredditSortedByTopOfMonth
    @Test
    public void givenSubredditName_whenFindAllPostsBySubredditByTopOfMonth_thenReturnPostList() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime lastMonth = currentDate.minusMonths(1L);

        given(postRepository.findAllPostsBySubredditSortedByTopOfMonth(eq("subredditName"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(List.of(mock(PostDto.class), mock(PostDto.class)));

        List<PostDto> postList = postService.findAllPostsBySubredditSortedByTopOfMonth("subredditName");

        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(2);
    }

    //test for findAllPostsBySubredditSortedByTopOfYear
    @Test
    public void givenSubredditName_whenFindAllPostsBySubredditByTopOfYear_thenReturnPostList() {
        given(postRepository.findAllPostsBySubredditSortedByTopOfYear(eq("subredditName"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(List.of(mock(PostDto.class), mock(PostDto.class)));

        List<PostDto> postList = postService.findAllPostsBySubredditSortedByTopOfYear("subredditName");

        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(2);
    }

    //test for findAllPostsBySubredditSortedByTopOfAllTime
    @Test
    public void givenSubredditName_whenFindAllPostsBySubredditByTopOfAllTime_thenReturnPostList() {
        given(postRepository.findAllPostsBySubredditSortedByTopOfAllTime("subredditName"))
                .willReturn(List.of(mock(PostDto.class), mock(PostDto.class)));

        List<PostDto> postList = postService.findAllPostsBySubredditSortedByTopOfAllTime("subredditName");

        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(2);
    }

    //test for findAllPostsBySubredditUser
    @Test
    public void givenUsername_whenFindAllPostsByUser_thenReturnPostList() {
        given(postRepository.findAllPostDtosByUsernameByDateSubmittedAsc("username"))
                .willReturn(List.of(mock(PostDto.class), mock(PostDto.class)));

        List<PostDto> postList = postService.findAllPostsByUser("username");

        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(2);
    }
}
