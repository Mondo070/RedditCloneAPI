package com.redditclone.reddit;

import com.redditclone.reddit.comment.CommentDto;
import com.redditclone.reddit.comment.CommentEntity;
import com.redditclone.reddit.comment.CommentRepository;
import com.redditclone.reddit.comment.CommentService;
import com.redditclone.reddit.post.PostEntity;
import com.redditclone.reddit.user.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private CommentEntity comment;

    @BeforeEach
    public void setup() {
        CommentEntity comment = Mockito.mock(CommentEntity.class);
        UserEntity user = Mockito.mock(UserEntity.class);
        PostEntity post = Mockito.mock(PostEntity.class);

        comment = CommentEntity.builder()
                .id(1L)
                .text("test")
                .dateSubmitted(LocalDateTime.now())
                .upvotes(1)
                .replies(List.of(comment))
                .post(post)
                .parent(null)
                .user(user)
                .build();
    }

    @Test
    public void givenCommentObject_whenCreateComment_thenReturnCommentObject() {
        given(commentRepository.save(comment)).willReturn(comment);

        CommentEntity savedComment = commentService.createComment(comment);

        assertThat(savedComment).isNotNull();
    }

    //test for updateComment method
    @Test
    public void givenCommentObject_whenUpdateComment_thenReturnUpdatedComment(){
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));
        given(commentRepository.save(comment)).willReturn(comment);
        comment.setText("Updated text");

        CommentEntity updatedComment = commentService.updateComment(comment.getId(), "Updated text");

        assertThat(updatedComment.getText()).isEqualTo("Updated text");
    }

    //test for updateComment method
    @Test
    public void givenNonExistentComment_whenUpdateComment_thenThrowsException(){
        long nonExistentPostId = 2L;

        given(commentRepository.findById(nonExistentPostId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            commentService.updateComment(nonExistentPostId, "Test");
        });

        verify(commentRepository, never()).save(any(CommentEntity.class));
    }

    //test for deletePost method
    @Test
    public void givenPostId_whenDeletePost_thenNothing(){
        willDoNothing().given(commentRepository).deleteById(1L);

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void givenCommentId_whenFindCommentById_thenReturnCommentDto() {
        given(commentRepository.findCommentDtoById(comment.getId())).willReturn(mock(CommentDto.class));

        CommentDto commentDto = commentService.getCommentById(comment.getId());

        assertThat(commentDto).isNotNull();
    }

    @Test
    public void givenCommentId_whenFindCommentById_thenReturnNull() {
        given(commentRepository.findCommentDtoById(10L)).willReturn(null);

        CommentDto commentDto = commentService.getCommentById(10L);

        assertThat(commentDto).isNull();
    }

    //test for findAllPostDtosByDateSubmittedAsc method
    @Test
    public void givenPostId_whenFindAllCommentsByPostId_thenReturnCommentList() {
        given(commentRepository.getCommentsAndReplies(1L))
                .willReturn(List.of(mock(CommentDto.class),mock(CommentDto.class)));

        List<CommentDto> commentList = commentService.findAllCommentsByPostId(1L);

        assertThat(commentList).isNotNull();
        assertThat(commentList.size()).isEqualTo(2);
    }

}
