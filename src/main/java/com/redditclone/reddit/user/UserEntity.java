package com.redditclone.reddit.user;

import com.redditclone.reddit.comment.CommentEntity;
import com.redditclone.reddit.post.PostEntity;
import com.redditclone.reddit.subreddit.SubredditEntity;
import com.redditclone.reddit.upvote.UpvoteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;
    private String username;

    private LocalDateTime dateJoined;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UpvoteEntity> upvotes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "subreddit_subscriptions",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="subreddit_id")
    )
    private Set<SubredditEntity> subscribedSubreddits = new HashSet<>();

    public UserEntity(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getDateJoined() {
        return dateJoined;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<SubredditEntity> getSubscribedSubreddits() {
        return subscribedSubreddits;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSubscribedSubreddits(Set<SubredditEntity> subscribedSubreddits) {
        this.subscribedSubreddits = subscribedSubreddits;
    }

    //    public void addSubreddit(SubredditEntity subreddit) {
//        this.subscribedSubreddits.add(subreddit);
//    }

//    public void removeSubreddit(SubredditEntity subreddit) {
//        this.subscribedSubreddits.remove(subreddit);
//        subreddit.getSubscribers().remove(this);
//    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
