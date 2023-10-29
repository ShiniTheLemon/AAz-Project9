//package com.sloth.net.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.stereotype.Service;
//
//import com.sloth.net.entities.Messages;
//import com.sloth.net.pojo.MessageStatus;
//
//@Service
//public interface MessagesService {
//	public Optional<String>getChatId(int senderId,int recipientId,boolean createIfExist);
//	public Messages msg(Messages msg);
//	public long countNewMesssages(int senderId,int recipientId);
//	public List<Messages>findMessages(int senderId,int recipientId);
//	public Messages findMessagesById(int id);
//	public void updateMessageStatus(int senderId, int recipientId,MessageStatus status);
//}
