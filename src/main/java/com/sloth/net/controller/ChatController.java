//package com.sloth.net.controller;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.sloth.net.entities.Messages;
//import com.sloth.net.pojo.Notifications;
//import com.sloth.net.service.MessagesService;
//
//import lombok.var;
//
//@Controller
//
//public class ChatController {
//	@Autowired 
//	private SimpMessagingTemplate messagingTemp;
//	@Autowired 
//	private MessagesService msgService;
//	
//	@MessageMapping("/chat")
//	public ResponseEntity<?> processMessage(@Payload Messages msg) {
//		Optional<String> chatId=msgService.getChatId(msg.getSenderid(), msg.getRecipientid(), true);
//		msg.setChatid(chatId.get());
//		
//		Messages saved=msgService.msg(msg);
//		messagingTemp.convertAndSendToUser(String.valueOf(msg.getRecipientid()),"/queue/messages",
//				new Notifications(
//						String.valueOf(saved.getMsgid()),
//						String.valueOf(saved.getSenderid())));
//		return ResponseEntity.ok(saved);
//	}
//	@GetMapping("/messages/{senderid}/{recipientid}/count")
//	public ResponseEntity<Long>countMessages(@PathVariable int senderid,@PathVariable int  recipientid){
//		return ResponseEntity.ok(msgService.countNewMesssages(senderid, recipientid));
//	}
//	@GetMapping("/messages/{senderid}/{recipientid}")
//	public ResponseEntity<?>findMessagesBySenderRecipient(@PathVariable int senderid,@PathVariable int recipientid){
//		return ResponseEntity.ok(msgService.findMessages(senderid, senderid));
//	}
//	@GetMapping("/messages/{id}")
//	public ResponseEntity<?>findMessagesById(@PathVariable int id){
//		return ResponseEntity.ok(msgService.findMessagesById(id));
//	}
//}
