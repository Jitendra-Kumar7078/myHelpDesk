package com.mahaveer.myHelpDesk.repository;

import com.mahaveer.myHelpDesk.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation,Long> {
    List<Conversation> findByTicketIdOrderByCreatedAtAsc(Long ticketId);
}
