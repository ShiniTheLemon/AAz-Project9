package com.sloth.net.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Playlist {
	@Id
	private int id;
	private int listid;
	private int userid;
	private String song;
	private String url;
}
