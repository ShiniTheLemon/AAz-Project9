package com.sloth.net.entities;



import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

public class Users {
	@Id
	private int user_id;
	private String email;
	private String password;
	private String role;
	public Users() {
	}
	public Users(String email,String password){
		this.email=email;
		this.password=password;
	}

}
