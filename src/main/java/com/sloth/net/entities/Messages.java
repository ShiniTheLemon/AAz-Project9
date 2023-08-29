package com.sloth.net.entities;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.sloth.net.pojo.MessageStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Messages {
	@Id
	private int msgid;
	private String chatid;
	private int senderid;
	private int recipientid;
	private String  content;
	private Time time;
	private MessageStatus status;
}
