package com.sloth.net.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sloth.net.entities.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	public Users findUsersByEmail(String email);
}
