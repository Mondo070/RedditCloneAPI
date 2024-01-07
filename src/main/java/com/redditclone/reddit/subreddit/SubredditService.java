package com.redditclone.reddit.subreddit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubredditService {

    private SubredditRepository repo;

    public SubredditEntity createSubreddit(SubredditEntity subreddit) {
        return repo.save(subreddit);
    }

    public void deleteSubreddit(long id) {
        repo.deleteById(id);
    }

    public SubredditDto findSubredditById(long id) {
        return repo.findSubredditDtoById(id);
    }

    public SubredditDto findSubredditByName(String name) {
        return repo.findSubredditByName(name);
    }

    public List<SubredditDto> findSubredditsByPrefix(String prefix) {
        return repo.findSubredditsByPrefix(prefix);
    }

}
