package com.redditclone.reddit.user;

import com.redditclone.reddit.subreddit.SubredditDto;
import com.redditclone.reddit.subreddit.SubredditEntity;
import com.redditclone.reddit.subreddit.SubredditRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

        private final UserRepository userRepo;
        private final SubredditRepository subredditRepo;

        public UserEntity createUser(UserEntity user) {
            return userRepo.save(user);
        }

        public void deleteUser(long id) {
            userRepo.deleteById(id);
        }

        public UserEntity editEmail(long userId, String updatedEmail) {
            Optional<UserEntity> userOptional = userRepo.findById(userId);
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                user.setEmail(updatedEmail);
                return userRepo.save(user);
            } else {
                throw new EntityNotFoundException("User not found with id: " + userId);
            }
        }

        public UserDto findUserById(long userId) {
            return userRepo.findUserDtoById(userId);
        }

        public UserDto findUserByUsername(String username) {
            return userRepo.findUserDtoByUsername(username);
        }

        public List<SubredditDto> findAllSubredditSubsByUserId(long userId) {
            return subredditRepo.findAllSubredditsByUserId(userId);
        }

        public void addSubredditSubToUser(long subredditId, long userId) {
            Optional<UserEntity> user = userRepo.findById(userId);
            Optional<SubredditEntity> sub = subredditRepo.findById(subredditId);

            if (user.isPresent() && sub.isPresent()) {
                Set<SubredditEntity> subscribedSubreddits = new HashSet<>(user.get().getSubscribedSubreddits());
                subscribedSubreddits.add(sub.get());
                user.get().setSubscribedSubreddits(subscribedSubreddits);

//                user.get().getSubscribedSubreddits().add(sub.get());
                userRepo.save(user.get());
                subredditRepo.save(sub.get());
            }
        }

        public void removeSubredditSubFromUser(long subredditId, long userId) {
            Optional<UserEntity> user = userRepo.findById(userId);
            Optional<SubredditEntity> sub = subredditRepo.findById(subredditId);

            if (user.isPresent() && sub.isPresent()) {
                Set<SubredditEntity> subscribedSubreddits = new HashSet<>(user.get().getSubscribedSubreddits());
                Set<UserEntity> subscribers = new HashSet<>(sub.get().getSubscribers());
                subscribedSubreddits.remove(sub.get());
                user.get().setSubscribedSubreddits(subscribedSubreddits);

                subscribers.remove(user.get());
                sub.get().setSubscribers(subscribers);

//                user.get().getSubscribedSubreddits().remove(sub.get());
//                sub.get().getSubscribers().remove(user.get());
//
//                user.get().removeSubreddit(sub.get());
                userRepo.save(user.get());
                subredditRepo.save(sub.get());
            }
        }
}
