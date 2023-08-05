package com.sloth.net.entities;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Messages {
	@Id
	private int msg_id;
	private int sender_id;
	private int recipient;
	private String  content;
	private Time time;
}
