package com.redditclone.reddit.upvote;

import com.redditclone.reddit.comment.CommentEntity;
import com.redditclone.reddit.post.PostEntity;
import com.redditclone.reddit.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "upvotes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpvoteEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="posts_id_seq")
    @SequenceGenerator(name="posts_id_seq", sequenceName="posts_id_seq", allocationSize=1)
    private Long id;
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UpvoteStatus status;

    @Enumerated(EnumType.STRING)
    private UpvotableType upvotableType;

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CommentEntity getComment() {
        return comment;
    }

    public void setComment(CommentEntity comment) {
        this.comment = comment;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public UpvoteStatus getStatus() {
        return status;
    }

    public void setStatus(UpvoteStatus status) {
        this.status = status;
    }

    public UpvotableType getUpvotableType() {
        return upvotableType;
    }

    public void setUpvotableType(UpvotableType upvotableType) {
        this.upvotableType = upvotableType;
    }

    @Override
    public String toString() {
        return "UpvoteEntity{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", user=" + user.getUsername() +
                ", status=" + status +
                '}';
    }
}
