package com.redditclone.reddit.upvote;

import com.redditclone.reddit.comment.CommentEntity;
import com.redditclone.reddit.post.PostEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpvoteService {

    private final UpvoteRepository repo;

    public UpvoteEntity createUpvote(UpvoteEntity upvote) {
        return repo.save(upvote);
    }

    public UpvoteEntity changeVote(Long upvoteId, UpvoteStatus status) {
        Optional<UpvoteEntity> voteOptional = repo.findById(upvoteId);
        if (voteOptional.isPresent()) {
            UpvoteEntity upvote = voteOptional.get();

            if (upvote.getStatus() == status) {
                return upvote;
            } else if (upvote.getComment() == null) {
                UpvoteStatus currentStatus = upvote.getStatus();
                PostEntity post = upvote.getPost();
                Integer currUpvotes = post.getUpvotes();
                if (status == UpvoteStatus.UPVOTED) {
                    if (currentStatus == UpvoteStatus.NEUTRAL) {
                        post.setUpvotes(currUpvotes-1);
                    } else {
                        post.setUpvotes(currUpvotes-2);
                    }
                } else if (status == UpvoteStatus.DOWNVOTED) {
                    if (currentStatus == UpvoteStatus.NEUTRAL) {
                        post.setUpvotes(currUpvotes-1);
                    } else {
                        post.setUpvotes(currUpvotes+2);
                    }
                } else if (status == UpvoteStatus.NEUTRAL) {
                    if (currentStatus == UpvoteStatus.UPVOTED) {
                        post.setUpvotes(currUpvotes+1);
                    } else {
                        post.setUpvotes(currUpvotes-1);
                    }
                }
            } else {
                UpvoteStatus currentStatus = upvote.getStatus();
                CommentEntity comment = upvote.getComment();
                Integer currUpvotes = comment.getUpvotes();
                if (status == UpvoteStatus.UPVOTED) {
                    if (currentStatus == UpvoteStatus.NEUTRAL) {
                        comment.setUpvotes(currUpvotes-1);
                    } else {
                        comment.setUpvotes(currUpvotes-2);
                    }
                } else if (status == UpvoteStatus.DOWNVOTED) {
                    if (currentStatus == UpvoteStatus.NEUTRAL) {
                        comment.setUpvotes(currUpvotes-1);
                    } else {
                        comment.setUpvotes(currUpvotes+2);
                    }
                } else if (status == UpvoteStatus.NEUTRAL) {
                    if (currentStatus == UpvoteStatus.UPVOTED) {
                        comment.setUpvotes(currUpvotes+1);
                    } else {
                        comment.setUpvotes(currUpvotes-1);
                    }
                }
            }

            upvote.setStatus(status);
            return repo.save(upvote);
        } else {
            throw new EntityNotFoundException("Post not found with id: " + upvoteId);
        }
    }

    public UpvoteDto getUpvoteByUpvotableId(Long upvotableId, UpvotableType upvotableType) {
        if (upvotableType.equals(UpvotableType.POST)) {
            return repo.findUpvoteDtoByUpvotableId(null, upvotableId);
        } else if ((upvotableType.equals(UpvotableType.COMMENT))) {
            return repo.findUpvoteDtoByUpvotableId(upvotableId, null);
        }
        return null;
    }

}
