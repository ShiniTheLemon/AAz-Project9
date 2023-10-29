//package com.sloth.net.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import javax.persistence.Query;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaUpdate;
//
//import org.hibernate.Criteria;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.provider.HibernateUtils;
//import org.springframework.stereotype.Service;
//
//import com.sloth.net.entities.Chat_room;
//import com.sloth.net.entities.Messages;
//import com.sloth.net.pojo.MessageStatus;
//import com.sloth.net.repo.ChatRoomRepository;
//import com.sloth.net.repo.MeassagesRepository;
//import com.sloth.net.service.exceptions.ResourceNotFoundException;
//
//import lombok.var;
//
//@Service
//public class MessagesServiceImp implements MessagesService{
//@Autowired
//ChatRoomRepository chatRepo;
//@Autowired
//MeassagesRepository msgRepo;
//@Autowired
//private SessionFactory sessionFactory;
//	@Override
//	public Optional<String> getChatId(int senderId, int recipientId, boolean createIfNotExist) {
//		// TODO Auto-generated method stub
//		return chatRepo.findBySenderidAndRecipientid(senderId, recipientId)
//				.map(Chat_room::getSr_id)
//				.or(() -> {
//                    if(!createIfNotExist) {
//                        return  Optional.empty();
//                    }
//                 //?   
//                    String  sr_id= String.format("%s_%s", senderId,recipientId);
//                
//               Chat_room senderRecipient=Chat_room
//            		   .builder()
//            		   .sr_id(sr_id)
//            		   .senderid(senderId)
//            		   .recipientid(recipientId)
//            		   .build();
//               Chat_room recipientSender= Chat_room
//            		   .builder()
//            		   .sr_id(sr_id)
//            		   .senderid(recipientId)
//            		   .recipientid(senderId)
//            		   .build();
//               
//               chatRepo.save(senderRecipient);
//               chatRepo.save(recipientSender);
//               
//               return Optional.of(sr_id);
//				});
//				}
//	@Override
//	public Messages msg(Messages msg) {
//		// TODO Auto-generated method stub
//		msg.setStatus(MessageStatus.RECIEVED);
//		msgRepo.save(msg);
//		return msg;
//	}
//	
//	@Override
//	public long countNewMesssages(int senderId, int recipientId) {
//		// TODO Auto-generated method stub
//		return msgRepo.countBySenderidAndRecipientidAndStatus(senderId, recipientId, MessageStatus.RECIEVED);
//	}
//	
//	//?????
//	@Override
//	public List<Messages> findMessages(int senderId, int recipientId) {
//		// TODO Auto-generated method stub
//		Optional<String> chatId=getChatId(senderId,recipientId,false);
//		List<Messages>  messages=chatId.map(msgid -> msgRepo.findByChatid(msgid)).orElse(new ArrayList<>());
//		
//		
//		if(messages.size()>0) {
//			updateMessageStatus(senderId,recipientId,MessageStatus.DELIVERED);
//		}
//		
//		return messages;
//	}
//	@Override
//	public Messages findMessagesById(int id) {
//		// TODO Auto-generated method stub
//		return msgRepo.findById(id).map(message->{
//			message.setStatus(MessageStatus.DELIVERED);
//			return msgRepo.save(message);
//		}).orElseThrow(()->
//		 new ResourceNotFoundException("can't find message (" + id + ")"));
//	}
//	@Override
//	public void updateMessageStatus(int senderId, int recipientId, MessageStatus status) {
//		// TODO Auto-generated method stub
//		Session session = sessionFactory.openSession();
//		Transaction trans = null;
//		try {
//			trans.begin();
//			Query query=session.createQuery(
//		            "UPDATE Messages c SET c.status = :status"
//		                    + " WHERE c.senderid = :senderid AND c.recipientid = :recipientid"
//		                );
//		                query.setParameter("senderid", senderId);
//		                query.setParameter("recipientid", recipientId);
//		                query.setParameter("status", status);
//		                query.executeUpdate();
//		}catch(HibernateException e) {
//	        if (trans != null) trans.rollback();
//	        throw e;
//	    } finally {
//	        session.close();
//	    }
//                
//	}}
//
//
