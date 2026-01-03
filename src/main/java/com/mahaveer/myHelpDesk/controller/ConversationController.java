package com.mahaveer.myHelpDesk.controller;

import com.mahaveer.myHelpDesk.model.Conversation;
import com.mahaveer.myHelpDesk.repository.ConversationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {

    private final ConversationRepository conversationRepository;

    public ConversationController(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @GetMapping("/{ticketId}")
    public List<Conversation> getConversation(@PathVariable Long ticketId) {
        return conversationRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }
}
