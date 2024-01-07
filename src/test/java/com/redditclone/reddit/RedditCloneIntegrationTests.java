package com.redditclone.reddit;

import com.redditclone.reddit.comment.CommentDto;
import com.redditclone.reddit.comment.CommentEntity;
import com.redditclone.reddit.comment.CommentRepository;
import com.redditclone.reddit.post.PostDto;
import com.redditclone.reddit.post.PostEntity;
import com.redditclone.reddit.post.PostRepository;
import com.redditclone.reddit.post.PostService;
import com.redditclone.reddit.subreddit.SubredditEntity;
import com.redditclone.reddit.subreddit.SubredditRepository;
import com.redditclone.reddit.user.UserEntity;
import com.redditclone.reddit.user.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedditCloneIntegrationTests {

	@Autowired
    CommentRepository commentRepository;

	@Autowired
    PostRepository postRepository;

	@Autowired
    SubredditRepository subredditRepository;

	@Autowired
    UserRepository userRepository;

	@Autowired
    PostService postService;

	@Container
	@ServiceConnection
	private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

	static {
		container.start();
	}

	@Test
	void contextLoads() {
		System.out.println("good");
	}

//	@Test
//	void postsAndCommentsWork() {
//		PostEntity post1 = new PostEntity("First Post", "this is my first post", LocalDate.now());
//		PostEntity post2 = new PostEntity("Second Post", "this is my second post", LocalDate.now());
//
//		List<PostEntity> posts = new ArrayList<>();
//		posts.add(post1);
//		posts.add(post2);
//
//		postRepository.saveAll(posts);
//
//		PostEntity firstPost = postRepository.getReferenceById(101L);
//
//		CommentEntity comment1 = new CommentEntity("hey this is a cool post", LocalDate.now());
//		comment1.setPost(firstPost);
//		CommentEntity comment2 = new CommentEntity("hey this post sucks", LocalDate.now());
//		comment2.setPost(firstPost);
////
//		List<CommentEntity> comments = new ArrayList<>();
//		comments.add(comment1);
//		comments.add(comment2);
//
////		firstPost.addComment(comment1);
////		firstPost.addComment(comment2);
//
//		commentRepository.saveAll(comments);
//
//		List<PostCommentRecord> records = commentRepository.findCommentRecordByTitle("First Post");
//		System.out.println(records);
//	}

	@Test
	void returnsAllPosts() {
		List<PostDto> posts = postRepository.findAllPostDtosByDateSubmittedAsc();
		PostDto post = postRepository.findPostDtoById(101);
		System.out.println(posts);
		System.out.println(post);
	}

	@Test
	void serviceReturnsAllPosts() {
		List<PostDto> posts = postService.findAllPostsSortedByNew();
		System.out.println(posts);
	}

	@Test
	void returnsAllPostsBySubreddit() {
		System.out.println("NBA posts:");
		List<PostDto> nbaPosts = postRepository.findAllPostDtosBySubredditByDateSubmittedAsc("NBA");
		System.out.println(nbaPosts);

		System.out.println("NFL posts:");
		List<PostDto> nflPosts = postRepository.findAllPostDtosBySubredditByDateSubmittedAsc("NFL");
		System.out.println(nflPosts);

		System.out.println("MLB posts:");
		List<PostDto> mlbPosts = postRepository.findAllPostDtosBySubredditByDateSubmittedAsc("MLB");
		System.out.println(mlbPosts);
	}

	@Test
	void returnsAllPostsByUsername() {
		System.out.println("Armando's posts:");
		List<PostDto> armandoPosts = postRepository.findAllPostDtosByUsernameByDateSubmittedAsc("Armando");
		System.out.println(armandoPosts);

		System.out.println("Armani's posts:");
		List<PostDto> armaniPosts = postRepository.findAllPostDtosByUsernameByDateSubmittedAsc("Armani");
		System.out.println(armaniPosts);

		System.out.println("Adrian's posts:");
		List<PostDto> adrianPosts = postRepository.findAllPostDtosByUsernameByDateSubmittedAsc("Adrian");
		System.out.println(adrianPosts);
	}

	@Test
	@Transactional
	void returnAllSubscribedSubredditsByUserId() {
		Optional<UserEntity> armando = userRepository.findById((long)101);
		Optional<UserEntity> armani = userRepository.findById((long)102);
		Optional<SubredditEntity> sub1 = subredditRepository.findById((long)101);
		Optional<SubredditEntity> sub2 = subredditRepository.findById((long)102);


		if (armando.isPresent() && armani.isPresent() && sub1.isPresent() && sub2.isPresent()) {
			System.out.println("here");
//			armando.get().addSubreddit(sub1.get());
//			armando.get().addSubreddit(sub2.get());
//
//			armani.get().addSubreddit(sub1.get());

			userRepository.save(armando.get());
			userRepository.save(armani.get());

			subredditRepository.save(sub1.get());
			subredditRepository.save(sub2.get());
		}




		armando.ifPresent(userEntity ->
				System.out.println(subredditRepository.findAllSubredditsByUserId(armando.get().getId())));

		armani.ifPresent(userEntity ->
				System.out.println(subredditRepository.findAllSubredditsByUserId(armani.get().getId())));
	}

	@Test
	@Transactional
	void returnComments() {
		Optional<PostEntity> post = postRepository.findById((long)115);
		Optional<CommentEntity> c1 = commentRepository.findById((long)101);
		Optional<CommentEntity> c2 = commentRepository.findById((long)102);
		Optional<CommentEntity> c3 = commentRepository.findById((long)103);
		Optional<CommentEntity> c4 = commentRepository.findById((long)104);
		Optional<CommentEntity> c5 = commentRepository.findById((long)105);

		if (post.isPresent() && c1.isPresent() && c2.isPresent() && c3.isPresent() && c4.isPresent() && c5.isPresent()) {
			post.get().addComment(c1.get());
			post.get().addComment(c2.get());
			post.get().addComment(c3.get());
			post.get().addComment(c4.get());
			post.get().addComment(c5.get());

			postRepository.save(post.get());
			commentRepository.save(c1.get());
			commentRepository.save(c2.get());
			commentRepository.save(c3.get());
			commentRepository.save(c4.get());
			commentRepository.save(c5.get());

			System.out.println(post.get());
			System.out.println(c1.get());
			System.out.println(c2.get());
			System.out.println(c3.get());
			System.out.println(c4.get());
			System.out.println(c5.get());

			List<CommentDto> test = commentRepository.getCommentsAndReplies(post.get().getId());

			for(CommentDto c: test) {
				System.out.println(c.getId());
			}
		}


		//System.out.println(commentRepository.getCommentsAndReplies(post));
	}

}
