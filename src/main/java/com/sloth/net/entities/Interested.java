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
public class Interested {
	@Id
	private int int_id;
	private int user_id;
	private String sex;
	private String age;
	private String relationship;
}
