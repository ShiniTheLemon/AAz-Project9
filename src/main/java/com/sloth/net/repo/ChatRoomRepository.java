package com.sloth.net.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sloth.net.entities.Chat_room;
@Repository
public interface ChatRoomRepository extends JpaRepository<Chat_room, Integer> {
	 Optional<Chat_room> findBySenderidAndRecipientid(int senderId, int recipientId);
}
