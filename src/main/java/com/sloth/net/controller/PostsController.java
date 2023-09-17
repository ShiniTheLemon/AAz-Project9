package com.sloth.net.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sloth.net.entities.Comments;
import com.sloth.net.entities.Posts;
import com.sloth.net.entities.User_info;
import com.sloth.net.service.PostService;



@RestController
@RequestMapping("/api/posts")
public class PostsController {
	@Autowired
	private PostService posts;
	
	@PostMapping("/createPost")
	public ResponseEntity<Posts>  CreatePost(int user_id, String post) {
		return new ResponseEntity<Posts>(posts.createPost(user_id, post),HttpStatus.CREATED);
	}
	@GetMapping("/userPosts")
	public ResponseEntity<List<Posts>> showUserPosts(int user_id){
		return new ResponseEntity<List<Posts>>(posts.showUserPosts(user_id),HttpStatus.OK);
	}
	@GetMapping("/allPosts")
	public ResponseEntity<List<Posts>> showAllPosts(){
		return new ResponseEntity<List<Posts>>(posts.showAllPosts(),HttpStatus.OK);
	}
	
	@PostMapping("/likePost")
	public ResponseEntity<Posts>  likePost(int post_id) {
		return new ResponseEntity<Posts>(posts.likePost(post_id),HttpStatus.CREATED);
	}
	@PostMapping("/dislikePost")
	public ResponseEntity<Posts>  dislikePost(int post_id) {
		return new ResponseEntity<Posts>(posts.dislikePost(post_id),HttpStatus.CREATED);
	}
	@PostMapping("/deletePost")
	public ResponseEntity<?>  deletePost(int post_id) {
		posts.deletePost(post_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	
	@PostMapping("/comment")
	public ResponseEntity<Comments>  CreateComment(int user_id, String comment,int post_id) {
		return new ResponseEntity<Comments>(posts.comment(user_id, post_id, comment),HttpStatus.CREATED);
	}
	@GetMapping("/allComments")
	public ResponseEntity<List<Comments>> showAllComments(int post_id){
		return new ResponseEntity<List<Comments>>(posts.ShowAllComments(post_id),HttpStatus.OK);
	}
	@PostMapping("/likeComment")
	public ResponseEntity<Comments>  likeComment(int comment_id) {
		return new ResponseEntity<Comments>(posts.likeComment(comment_id),HttpStatus.CREATED);
	}
	@PostMapping("/dislikeComment")
	public ResponseEntity<Comments>  dislikeComment(int comment_id) {
		return new ResponseEntity<Comments>(posts.dislikeComment(comment_id),HttpStatus.CREATED);
	}
	@PostMapping("/deleteComment")
	public ResponseEntity<?>  deleteComment(int comment_id) {
		posts.deleteComment(comment_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	@PostMapping("/addInfo")
	public ResponseEntity<User_info>AddInfo(User_info info){
		return new ResponseEntity<User_info>(posts.addUserInfo(info),HttpStatus.CREATED);
	}
	@GetMapping("/showAllUsers")
	public ResponseEntity<List<User_info>>showAllUsers(){
		return new ResponseEntity<List<User_info>>(posts.getAllUserInfo(),HttpStatus.OK);
	}
	@PostMapping("/showUser")
	public ResponseEntity<Optional<User_info>>showUser(int user_id){
		return new ResponseEntity<Optional<User_info>>(posts.getUserInfo(user_id),HttpStatus.OK);
	}
}
