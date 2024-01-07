package com.redditclone.reddit.post;

import com.redditclone.reddit.upvote.BestComparator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository repo;

    public PostEntity createPost(PostEntity post) {
        post.setDateSubmitted(LocalDateTime.now());
        return repo.save(post);
    }

    public void deletePost(long id) {
        repo.deleteById(id);
    }

    public PostEntity updatePost(Long postId, String updatedText) {
        Optional<PostEntity> postOptional = repo.findById(postId);
        if (postOptional.isPresent()) {
            PostEntity post = postOptional.get();
            post.setText(updatedText);
            return repo.save(post);
        } else {
            throw new EntityNotFoundException("Post not found with id: " + postId);
        }
    }

    public PostDto getPostById(long id) {
        return repo.findPostDtoById(id);
    }

    public List<PostDto> findAllPostsSortedByNew() {
        return repo.findAllPostDtosByDateSubmittedAsc();
    }

    public List<PostDto> findAllPostsSortedByBest() {
        List<PostDto> list = repo.findAllPostDtosByDateSubmittedAsc();
        Comparator<PostDto> postComparator = new BestComparator();
        list.sort(postComparator);

        return list;
    }

    public List<PostDto> findAllPostsBySubredditSortedByNew(String subredditName) {
        return repo.findAllPostDtosBySubredditByDateSubmittedAsc(subredditName);
    }

    public List<PostDto> findAllPostsBySubredditSortedByTopOfHour(String subredditName) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime lastHour = currentDate.minusHours(1L);

        return repo.findAllPostsBySubredditSortedByTopOfHour(subredditName, currentDate, lastHour);
    }

    public List<PostDto> findAllPostsBySubredditSortedByTopOfDay(String subredditName) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime lastDay = currentDate.minusDays(1L);

        return repo.findAllPostsBySubredditSortedByTopOfDay(subredditName, currentDate, lastDay);
    }

    public List<PostDto> findAllPostsBySubredditSortedByTopOfWeek(String subredditName) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime lastWeek = currentDate.minusWeeks(1L);

        return repo.findAllPostsBySubredditSortedByTopOfWeek(subredditName, currentDate, lastWeek);
    }

    public List<PostDto> findAllPostsBySubredditSortedByTopOfMonth(String subredditName) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime lastMonth = currentDate.minusMonths(1L);

        return repo.findAllPostsBySubredditSortedByTopOfMonth(subredditName, currentDate, lastMonth);
    }

    public List<PostDto> findAllPostsBySubredditSortedByTopOfYear(String subredditName) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime lastYear = currentDate.minusYears(1L);

        return repo.findAllPostsBySubredditSortedByTopOfYear(subredditName, currentDate, lastYear);
    }

    public List<PostDto> findAllPostsBySubredditSortedByTopOfAllTime(String subredditName) {
        return repo.findAllPostsBySubredditSortedByTopOfAllTime(subredditName);
    }

    public List<PostDto> findAllPostsBySubredditSortedByBest(String subredditName) {
        List<PostDto> list = repo.findAllPostDtosBySubredditByDateSubmittedAsc(subredditName);
        Comparator<PostDto> postComparator = new BestComparator();
        list.sort(postComparator);

        return list;
    }

    public List<PostDto> findAllPostsByUser(String username) {
        return repo.findAllPostDtosByUsernameByDateSubmittedAsc(username);
    }

}
