package com.sloth.net.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sloth.net.entities.User_info;
@Repository
public interface UserInfoRepository extends JpaRepository<User_info, Integer> {
	Optional<User_info> findByUserid(int id);
}
