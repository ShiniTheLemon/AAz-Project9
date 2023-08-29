package com.sloth.net.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chat_room {
	@Id
	private int cr_id;
	private String sr_id;
	private int senderid;
	private int recipientid;
}
