package com.sloth.net.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
	@Id
	private int cid;
	private int pid;
	private int userid;
	private String comment;
	private int likes;
	private int dislikes;
}
