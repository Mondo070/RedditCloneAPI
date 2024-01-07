package com.redditclone.reddit.subreddit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SubredditController {
    private final SubredditService subredditService;

    @Autowired
    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @PostMapping("/createSubreddit")
    public ResponseEntity<SubredditEntity> createSubreddit(@RequestBody SubredditEntity subreddit) {
        SubredditEntity createdSubreddit = subredditService.createSubreddit(subreddit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubreddit);
    }

    @DeleteMapping("subreddit/{id}")
    public void deleteSubreddit(@PathVariable Long id) {
        subredditService.deleteSubreddit(id);
    }

    @GetMapping("subreddit/{prefix}")
    public List<SubredditDto> searchSubredditsByPrefix(@PathVariable("prefix") String prefix) {
        return subredditService.findSubredditsByPrefix(prefix);
    }

    @GetMapping("subreddit/{id}")
    public ResponseEntity<SubredditDto> findSubredditById(@PathVariable Long id) {
        SubredditDto subreddit = subredditService.findSubredditById(id);
        if (subreddit != null) {
            return ResponseEntity.ok(subreddit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("subreddit/{name}")
    public ResponseEntity<SubredditDto> findSubredditByName(@PathVariable String name) {
        SubredditDto subreddit = subredditService.findSubredditByName(name);
        if (subreddit != null) {
            return ResponseEntity.ok(subreddit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
