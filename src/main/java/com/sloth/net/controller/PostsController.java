package com.sloth.net.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/createPost/{user_id}/{topic}/{post}")
	public ResponseEntity<Posts>  CreatePost(@PathVariable int user_id,@PathVariable String topic,@PathVariable  String post) {
		return new ResponseEntity<Posts>(posts.createPost(user_id, post,topic),HttpStatus.CREATED);
	}
	@GetMapping("/userPosts/{user_id}")
	public ResponseEntity<List<Posts>> showUserPosts(@PathVariable int user_id){
		return new ResponseEntity<List<Posts>>(posts.showUserPosts(user_id),HttpStatus.OK);
	}
	@GetMapping("/allPosts")
	public ResponseEntity<List<Posts>> showAllPosts(){
		return new ResponseEntity<List<Posts>>(posts.showAllPosts(),HttpStatus.OK);
	}
	@GetMapping("/singlePosts/{post_id}")
	public ResponseEntity<Posts> showSinglePost(@PathVariable int post_id){
		return new ResponseEntity<Posts>(posts.showSinglePost(post_id),HttpStatus.OK);
	}
	
	@PostMapping("/likePost/{post_id}")
	public ResponseEntity<Posts>  likePost(@PathVariable int post_id) {
		return new ResponseEntity<Posts>(posts.likePost(post_id),HttpStatus.CREATED);
	}
	@PostMapping("/dislikePost/{post_id}")
	public ResponseEntity<Posts>  dislikePost(@PathVariable int post_id) {
		return new ResponseEntity<Posts>(posts.dislikePost(post_id),HttpStatus.CREATED);
	}
	@PostMapping("/deletePost/{post_id}")
	public ResponseEntity<?>  deletePost(@PathVariable int post_id) {
		posts.deletePost(post_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PostMapping("/editPost/{post_id}/{post}")
	public ResponseEntity<?>  editPost(@PathVariable int post_id,String post) {
		return new ResponseEntity<>(posts.editPost(post_id,post),HttpStatus.OK);
	}
	
	
	
	
	@PostMapping("/comment/{user_id}/{comment}/{post_id}")
	public ResponseEntity<List<Comments>>  CreateComment(@PathVariable int user_id,@PathVariable String comment,@PathVariable int post_id) {
		return new ResponseEntity<List<Comments>>(posts.comment(user_id, post_id, comment),HttpStatus.CREATED);
	}
	@GetMapping("/allComments/{post_id}")
	public ResponseEntity<List<Comments>> showAllComments(@PathVariable int post_id){
		return new ResponseEntity<List<Comments>>(posts.ShowAllComments(post_id),HttpStatus.OK);
	}
	@PostMapping("/likeComment/{comment_id}")
	public ResponseEntity<List<Comments>>  likeComment(@PathVariable int comment_id) {
		return new ResponseEntity<List<Comments>>(posts.likeComment(comment_id),HttpStatus.CREATED);
	}
	@PostMapping("/dislikeComment/{comment_id}")
	public ResponseEntity<List<Comments>>  dislikeComment(@PathVariable int comment_id) {
		return new ResponseEntity<List<Comments>>(posts.dislikeComment(comment_id),HttpStatus.CREATED);
	}
	@PostMapping("/deleteComment")
	public ResponseEntity<?>  deleteComment(int comment_id) {
		posts.deleteComment(comment_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	@PostMapping("/addInfo")
	public ResponseEntity<User_info>AddInfo(@RequestBody User_info info){
		return new ResponseEntity<User_info>(posts.addUserInfo(info),HttpStatus.CREATED);
	}
	@GetMapping("/showAllUsers")
	public ResponseEntity<List<User_info>>showAllUsers(){
		return new ResponseEntity<List<User_info>>(posts.getAllUserInfo(),HttpStatus.OK);
	}
	@GetMapping("/showUser/{user_id}")
	public ResponseEntity<Optional<User_info>>showUser(@PathVariable int user_id){
		return new ResponseEntity<Optional<User_info>>(posts.getUserInfo(user_id),HttpStatus.OK);
	}
}
