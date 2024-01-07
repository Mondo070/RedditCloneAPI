package com.redditclone.reddit.user;

import com.redditclone.reddit.subreddit.SubredditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("user/{id}")
    public ResponseEntity<UserEntity> updatePost(@PathVariable Long id, @RequestBody String updatedEmail) {
        UserEntity updatedUser = userService.editEmail(id, updatedEmail);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        UserDto user = userService.findUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("user/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto user = userService.findUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("user/{id}")
    public void deletePost(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("subscriptions/{id}")
    public List<SubredditDto> getAllSubscriptionsByUserId(@PathVariable Long id) {
        return userService.findAllSubredditSubsByUserId(id);
    }

    @DeleteMapping("removeSub/{userId}/{subredditId}")
    public void removeSubscription(@PathVariable Long userId, @PathVariable Long subredditId) {
        userService.removeSubredditSubFromUser(subredditId, userId);
    }

    @PutMapping("addSub/{userId}/{subredditId}")
    public void addSubscription(@PathVariable Long userId, @PathVariable Long subredditId) {
        userService.addSubredditSubToUser(subredditId, userId);
    }

}
