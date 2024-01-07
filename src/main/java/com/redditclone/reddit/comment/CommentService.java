package com.redditclone.reddit.comment;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository repo;

    public CommentEntity createComment(CommentEntity comment) {
        return repo.save(comment);
    }

    public void deleteComment(long id) {
        repo.deleteById(id);
    }

    public CommentEntity updateComment(Long commentId, String updatedText) {
        Optional<CommentEntity> commentOptional = repo.findById(commentId);
        if (commentOptional.isPresent()) {
            CommentEntity comment = commentOptional.get();
            comment.setText(updatedText);
            return repo.save(comment);
        } else {
            throw new EntityNotFoundException(("Comment not found with id: " + commentId));
        }
    }

    public CommentDto getCommentById(long id) {
        return repo.findCommentDtoById(id);
    }

    public List<CommentDto> findAllCommentsByPostId(long postId) {
        return repo.getCommentsAndReplies(postId);
    }

    public List<CommentDto> findAllCommentsByParentCommentChain(long commentId) {
        return repo.getCommentChainByParentId(commentId);
    }

    public List<CommentDto> findAllCommentsByUserId(long userId) {
        return repo.findCommentsByUserID(userId);
    }

}
