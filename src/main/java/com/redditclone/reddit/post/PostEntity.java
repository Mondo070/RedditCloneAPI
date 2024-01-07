package com.redditclone.reddit.post;

import com.redditclone.reddit.comment.CommentEntity;
import com.redditclone.reddit.subreddit.SubredditEntity;
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
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity implements Upvotable {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="posts_id_seq")
    @SequenceGenerator(name="posts_id_seq", sequenceName="posts_id_seq", allocationSize=1)
    private Long id;
    private String title;
    private String text;
    private Integer upvotes;
    private LocalDateTime dateSubmitted;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subreddit_id")
    private SubredditEntity subreddit;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UpvoteEntity> upvoteList = new ArrayList<>();

    public PostEntity(String title, String text, LocalDateTime dateSubmitted) {
        this.title = title;
        this.text = text;
        this.dateSubmitted = dateSubmitted;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public LocalDateTime getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDateTime dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public SubredditEntity getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(SubredditEntity subreddit) {
        this.subreddit = subreddit;
    }

    public void addComment(CommentEntity comment) {
        comments.add(comment);
        comment.setPost(this);
    }


    @Override
    public String toString() {
        return "PostEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", dateSubmitted=" + dateSubmitted +
                ", user=" + user.getUsername() +
                ", subreddit=" + subreddit.getName() +
                '}';
    }
}
