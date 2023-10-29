package com.sloth.net.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sloth.net.entities.Playlist;
import com.sloth.net.repo.PlaylistRepository;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {
	@Autowired
	PlaylistRepository playlistRepo;
	
	@PostMapping("/song")
	public ResponseEntity<List<Playlist>> getPlaylist(@RequestBody Playlist song){
		return new ResponseEntity<List<Playlist>>(playlistRepo.findBySong(song.getSong()),HttpStatus.OK);
	}
}
