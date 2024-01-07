package com.redditclone.reddit.comment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {
    private final CommentService commentService;
    
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("postComments/{postId}")
    public List<CommentDto> getAllCommentsByCommentId(@PathVariable long postId) {
        return commentService.findAllCommentsByPostId(postId);
    }
    
    @GetMapping("userComments/{userId}")
    public List<CommentDto> getAllCommentsByUserId(@PathVariable long userId) {
        return commentService.findAllCommentsByUserId(userId);
    }
    
    @GetMapping("comments/chain/{id}")
    public List<CommentDto> getAllCommentsByParentCommentChain(@PathVariable long id) {
        return commentService.findAllCommentsByParentCommentChain(id);
    }

    @GetMapping("comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long id) {
        CommentDto comment = commentService.getCommentById(id);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentEntity> createComment(@RequestBody CommentEntity comment) {
        CommentEntity createdComment = commentService.createComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("comments/{id}")
    public ResponseEntity<CommentEntity> updateComment(@PathVariable Long id, @RequestBody String updatedText) {
        CommentEntity updatedComment = commentService.updateComment(id, updatedText);
        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

}
