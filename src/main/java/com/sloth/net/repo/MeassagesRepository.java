package com.sloth.net.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sloth.net.entities.Messages;
import com.sloth.net.pojo.MessageStatus;
@Repository
public interface MeassagesRepository extends JpaRepository< Messages,Integer> {
    long countBySenderidAndRecipientidAndStatus(
            int senderId, int recipientId, MessageStatus status);

    List<Messages> findByChatid(String chat_id);
}
