package com.sloth.net.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User_info {
	@Id
	private int infoid;
	private int userid;
	private String user_name;
	private String email;
	private String sex;
	private Date birthday;
	private String about;
	private String interests;
	private String night_activity;

}
