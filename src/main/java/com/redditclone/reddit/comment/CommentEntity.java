package com.redditclone.reddit.comment;

import com.redditclone.reddit.post.PostEntity;
import com.redditclone.reddit.upvote.Upvotable;
import com.redditclone.reddit.upvote.UpvoteEntity;
import com.redditclone.reddit.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity implements Upvotable {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="comments_id_seq")
    @SequenceGenerator(name="comments_id_seq", sequenceName="comments_id_seq", allocationSize=1)
    private Long id;
    private String text;
    private LocalDateTime dateSubmitted;
    private Integer upvotes;
    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "parentid")
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<CommentEntity> replies;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UpvoteEntity> upvoteList = new ArrayList<>();

    public CommentEntity(String text, LocalDateTime dateSubmitted) {
        this.text = text;
        this.dateSubmitted = dateSubmitted;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateSubmitted() {
        return dateSubmitted;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<CommentEntity> getReplies() {
        return this.replies;
    }

    //    public long getUserID() {
//        return userID;
//    }
//
//    public Long getPostID() {
//        return postID;
//    }


    @Override
    public String toString() {
        return "CommentEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", dateSubmitted=" + dateSubmitted +
                ", parent=" + parent +
                '}';
    }
}
