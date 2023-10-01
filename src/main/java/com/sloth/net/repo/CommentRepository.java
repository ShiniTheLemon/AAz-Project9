package com.sloth.net.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sloth.net.entities.Comments;
@Repository
public interface CommentRepository extends JpaRepository<Comments, Integer> {
	Comments findCommentsByCid(int id);
	List<Comments> findCommentsByPid(int id);
	void deleteByPid(int post_id);
}
