package com.sloth.net.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.sloth.net.entities.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

	List<Playlist> findBySong(String song);

}
