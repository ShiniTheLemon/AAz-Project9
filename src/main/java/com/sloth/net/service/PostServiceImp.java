package com.sloth.net.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sloth.net.entities.Comments;
import com.sloth.net.entities.Posts;
import com.sloth.net.repo.CommentRepository;
import com.sloth.net.repo.PostRepository;

@Service
public class PostServiceImp implements PostService{

	@Autowired
	CommentRepository commentRepo;
	@Autowired
	PostRepository postRepo;
	
	@Override
	public Posts createPost(int user_id, String post) {
		// TODO Auto-generated method stub
		
		Posts postsObj=new Posts();
		postsObj.setUserid(user_id);
		postsObj.setPost(post);
		
		return postRepo.save(postsObj);
		
	}

	@Override
	public Posts likePost(int post_id) {
		// TODO Auto-generated method stub
		Posts postsObj=new Posts();
		postsObj.setPid(post_id);
		
		//get total number of likes and then increment
		int likes=postRepo.findPostsByPid(post_id).getLikes()+1;
		postsObj.setLikes(likes);
		
		return postRepo.save(postsObj);
	}

	@Override
	public Posts dislikePost(int post_id) {
		// TODO Auto-generated method stub
		Posts postsObj=new Posts();
		postsObj.setPid(post_id);
		
		//get total number of dislikes and then increases them by one
		int dislikes=postRepo.findPostsByPid(post_id).getDislikes()+1;
		postsObj.setDislikes(dislikes);
		
		return postRepo.save(postsObj);
		
	}

	@Override
	public List<Posts> showUserPosts(int user_id) {
		// TODO Auto-generated method stub
		List<Posts> postList=postRepo.findPostsByUserid(user_id);
		return postList;
	}

	@Override
	public List<Posts> showAllPosts() {
		// TODO Auto-generated method stub
		List<Posts>postList=postRepo.findAll();
		return postList;
	}

	@Override
	public Comments comment(int user_id, int post_id, String comment) {
		// TODO Auto-generated method stub
		Comments commentObj=new Comments();
		commentObj.setUserid(user_id);
		commentObj.setPid(post_id);
		commentObj.setComment(comment);
		
		return commentRepo.save(commentObj);
	}

	@Override
	public Comments likeComment(int comment_id) {
		// TODO Auto-generated method stub
		Comments commentObj=commentRepo.findCommentsByCid(comment_id);
		
		//gets initial likes then adds one
		int likes=commentObj.getLikes()+1;
		commentObj.setLikes(likes);
		return commentRepo.save(commentObj);
		
	}

	@Override
	public Comments dislikeComment(int comment_id) {
		// TODO Auto-generated method stub
		Comments commentObj=commentRepo.findCommentsByCid(comment_id);
		
		//gets initial likes then adds one
		int dislikes=commentObj.getDislikes()+1;
		commentObj.setDislikes(dislikes);
		return commentRepo.save(commentObj);
		
	}

	@Override
	public List<Comments> ShowAllComments(int post_id) {
		// TODO Auto-generated method stub
		List<Comments> commentList=commentRepo.findCommentsByPid(post_id);
		return commentList;
	}

}
