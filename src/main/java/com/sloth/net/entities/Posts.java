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
public class Posts {
	@Id
	private int pid;
	private int userid;
	private String post;
	private int likes;
	private int dislikes;
}
