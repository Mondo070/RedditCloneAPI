package com.redditclone.reddit.upvote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UpvoteController {

    private final UpvoteService upvoteService;

    @Autowired
    public UpvoteController(UpvoteService upvoteService) {
        this.upvoteService = upvoteService;
    }

    @PostMapping("/createUpvote")
    public ResponseEntity<UpvoteEntity> createUpvote(@RequestBody UpvoteEntity upvote) {
        UpvoteEntity createdUpvote = upvoteService.createUpvote(upvote);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUpvote);
    }

    @PutMapping("upvote/{id}")
    public ResponseEntity<UpvoteEntity> changeUpvote(@PathVariable Long id, @RequestBody UpvoteStatus updatedStatus) {
        UpvoteEntity updatedUpvote = upvoteService.changeVote(id, updatedStatus);
        if (updatedUpvote != null) {
            return ResponseEntity.ok(updatedUpvote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("upvote/{id}")
    public ResponseEntity<UpvoteDto> getUpvoteById(@PathVariable long id, @RequestBody UpvotableType upvotableType) {
        UpvoteDto upvote = upvoteService.getUpvoteByUpvotableId(id, upvotableType);
        if (upvote != null) {
            return ResponseEntity.ok(upvote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
