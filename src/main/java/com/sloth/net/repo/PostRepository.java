package com.sloth.net.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sloth.net.entities.Posts;
@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {
	Posts findPostsByPid(int id);
	List<Posts> findPostsByUserid(int user_id);
	void deleteByPid(int post_id);
}
