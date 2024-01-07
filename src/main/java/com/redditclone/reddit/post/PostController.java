package com.redditclone.reddit.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/r")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all/new")
    public List<PostDto> getAllPostsByNew() {
        return postService.findAllPostsSortedByNew();
    }

    @GetMapping("all")
    public List<PostDto> getAllPostsByBest() {
        return postService.findAllPostsSortedByBest();
    }

    @GetMapping("/{subredditName}/new")
    public List<PostDto> getAllPostsBySubredditByNew(@PathVariable("subredditName") String subredditName) {
        return postService.findAllPostsBySubredditSortedByNew(subredditName);
    }

    @GetMapping("/{subredditName}/top/hour")
    public List<PostDto> getAllPostsBySubredditByTopOfHour(@PathVariable String subredditName) {
        return postService.findAllPostsBySubredditSortedByTopOfHour(subredditName);
    }

    @GetMapping("/{subredditName}/top/day")
    public List<PostDto> getAllPostsBySubredditByTopOfDay(@PathVariable String subredditName) {
        return postService.findAllPostsBySubredditSortedByTopOfDay(subredditName);
    }

    @GetMapping("/{subredditName}/top/week")
    public List<PostDto> getAllPostsBySubredditByTopOfWeek(@PathVariable String subredditName) {
        return postService.findAllPostsBySubredditSortedByTopOfWeek(subredditName);
    }

    @GetMapping("/{subredditName}/top/month")
    public List<PostDto> getAllPostsBySubredditByTopOfMonth(@PathVariable String subredditName) {
        return postService.findAllPostsBySubredditSortedByTopOfMonth(subredditName);
    }

    @GetMapping("/{subredditName}/top/year")
    public List<PostDto> getAllPostsBySubredditByTopOfYear(@PathVariable String subredditName) {
        return postService.findAllPostsBySubredditSortedByTopOfYear(subredditName);
    }

    @GetMapping("/{subredditName}/top/all-time")
    public List<PostDto> getAllPostsBySubredditByTopOfAllTime(@PathVariable String subredditName) {
        return postService.findAllPostsBySubredditSortedByTopOfAllTime(subredditName);
    }

    @GetMapping("/{subredditName}/best")
    public List<PostDto> getAllPostsBySubredditByBest(@PathVariable String subredditName) {
        return postService.findAllPostsBySubredditSortedByBest(subredditName);
    }

    @GetMapping("posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("posts/{username}")
    public List<PostDto> getAllPostsByUsername(@PathVariable("username") String username) {
        return postService.findAllPostsByUser(username);
    }

    @PostMapping("/submit")
    public ResponseEntity<PostEntity> createPost(@RequestBody PostEntity post) {
        PostEntity createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("posts/{id}")
    public ResponseEntity<PostEntity> updatePost(@PathVariable Long id, @RequestBody String updatedText) {
        PostEntity updatedPost = postService.updatePost(id, updatedText);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}
