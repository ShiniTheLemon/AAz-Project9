package com.sloth.net.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sloth.net.entities.Comments;
import com.sloth.net.entities.Posts;
import com.sloth.net.entities.User_info;
import com.sloth.net.entities.Users;
import com.sloth.net.repo.CommentRepository;
import com.sloth.net.repo.PostRepository;
import com.sloth.net.repo.UserInfoRepository;
import com.sloth.net.repo.UserRepository;

@Service
public class PostServiceImp implements PostService{

	@Autowired
	CommentRepository commentRepo;
	@Autowired
	PostRepository postRepo;
	@Autowired
	UserInfoRepository userInfoRepo;
	@Autowired
	UserRepository userRepo;
	
	//create method to prevent user from liking more than once
	
	@Override
	public Posts createPost(int user_id, String post,String topic) {
		// TODO Auto-generated method stub
		
		Posts postsObj=new Posts();
		postsObj.setUserid(user_id);
		postsObj.setPost(post);
		postsObj.setTopic(topic);
		
		return postRepo.save(postsObj);
		
	}
	
	@Override
	public Posts editPost( int post_id, String post) {
		// TODO Auto-generated method stub
		Posts postsObj=postRepo.findPostsByPid(post_id);
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
		return filterPosts(postList);
	}

	@Override
	public List<Posts> showAllPosts() {
		// TODO Auto-generated method stub
		List<Posts>postList=postRepo.findAll();
		
		return filterPosts(postList);
	}

	@Override
	public List<Comments> comment(int user_id, int post_id, String comment) {
		// TODO Auto-generated method stub
		Comments commentObj=new Comments();
		commentObj.setUserid(user_id);
		commentObj.setPid(post_id);
		commentObj.setComment(comment);
		
		 commentRepo.save(commentObj);
		 return filterComments(commentRepo.findCommentsByPid(post_id));
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
		return filterComments(commentList);
	}
	
	
	//checks every individual post then removes unpopular ones
	private List<Posts>filterPosts(List<Posts>postList){
		ArrayList<Posts>filteredList=new ArrayList<Posts>();
		
		for(int i=0;i<postList.size()-1;i++) {
			Posts postObj=postList.get(i);
			if(postObj.getDislikes()<postObj.getLikes()||postObj.getDislikes()==0) {
				filteredList.add(postObj);
			}
		}
	
		return filteredList;
	}
	//checks every individual comments then removes unpopular ones
	private List<Comments>filterComments(List<Comments>commentList){
		ArrayList<Comments>filteredComments=new ArrayList<Comments>();
		
		for(int i=0;i<commentList.size()-1;i++) {
			Comments commentObj=commentList.get(i);
			if(commentObj.getLikes()>commentObj.getDislikes()||commentObj.getDislikes()==0) {
				filteredComments.add(commentObj);
			}
		}
		return filteredComments;
	}

	@Override
	public void deletePost(int post_id) {
		// TODO Auto-generated method stub
		postRepo.deleteById(post_id);
		commentRepo.deleteByPid(post_id);
		
	}

	@Override
	public void deleteComment(int comment_id) {
		// TODO Auto-generated method stub
		commentRepo.deleteById(comment_id);
	}

	@Override
	public User_info addUserInfo(User_info info) {
		// TODO Auto-generated method stub
		User_info data=userInfoRepo.save(info);
		return data;
	}

	@Override
	public List<User_info> getAllUserInfo() {
		// TODO Auto-generated method stub
		List<User_info> data=userInfoRepo.findAll();
		return data;
	}

	@Override
	public Optional<User_info> getUserInfo(int user_id) {
		// TODO Auto-generated method stub
		Optional<User_info> data=userInfoRepo.findByUserid(user_id);
		
		return data;
	}

	@Override
	public Posts showSinglePost(int post_id) {
		// TODO Auto-generated method stub
		return postRepo.findPostsByPid(post_id);
	}





}
