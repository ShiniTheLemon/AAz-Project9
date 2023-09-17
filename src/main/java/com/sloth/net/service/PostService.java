package com.sloth.net.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sloth.net.entities.Comments;
import com.sloth.net.entities.Posts;
import com.sloth.net.entities.User_info;



@Service
public interface PostService {
	//posts
	public Posts createPost(int user_id,String post);
	public Posts likePost(int post_id);
	public Posts dislikePost(int post_id);
	public List<Posts> showUserPosts(int user_id);
	public List<Posts> showAllPosts();
	public void deletePost(int post_id);
	
	//comments
	public Comments comment(int user_id,int post_id,String comment);
	public Comments likeComment(int comment_id);
	public Comments dislikeComment(int comment_id);
	public List<Comments> ShowAllComments(int post_id);
	public void deleteComment(int comment_id);
	
	//user info
	public User_info addUserInfo(User_info info);
	public List<User_info>getAllUserInfo();
	public Optional<User_info> getUserInfo(int user_id);

}
