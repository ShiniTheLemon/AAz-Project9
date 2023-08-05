package com.sloth.net.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sloth.net.entities.Comments;
import com.sloth.net.entities.Posts;



@Service
public interface PostService {
	//posts
	public Posts createPost(int user_id,String post);
	public Posts likePost(int post_id);
	public Posts dislikePost(int post_id);
	public List<Posts> showUserPosts(int user_id);
	public List<Posts> showAllPosts();
	
	//comments
	public Comments comment(int user_id,int post_id,String comment);
	public Comments likeComment(int comment_id);
	public Comments dislikeComment(int comment_id);
	public List<Comments> ShowAllComments(int post_id);
}
