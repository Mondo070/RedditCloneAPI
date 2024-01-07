package com.redditclone.reddit.subreddit;

import com.redditclone.reddit.post.PostEntity;
import com.redditclone.reddit.user.UserEntity;
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
@Table(name = "subreddits")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubredditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subreddits_id_seq")
    @SequenceGenerator(name = "subreddits_id_seq", sequenceName = "subreddits_id_seq", allocationSize = 1)
    private Long id;
    private String name;

    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "subreddit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> posts = new ArrayList<>();

    @ManyToMany(mappedBy = "subscribedSubreddits")
    private Set<UserEntity> subscribers = new HashSet<>();

    public SubredditEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public Set<UserEntity> getSubscribers() {
        return subscribers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubscribers(Set<UserEntity> subscribers) {
        this.subscribers = subscribers;
    }

    //    public void addSubscriber(UserEntity user) {
//        this.subscribers.add(user);
//        user.getSubscribedSubreddits().add(this);
//    }
//
//    public void removeSubscriber(UserEntity user) {
//        this.subscribers.remove(user);
//        user.getSubscribedSubreddits().remove(this);
//    }

    @Override
    public String toString() {
        return "SubredditEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
