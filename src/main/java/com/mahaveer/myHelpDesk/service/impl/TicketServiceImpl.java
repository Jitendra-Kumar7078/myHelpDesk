package com.mahaveer.myHelpDesk.service.impl;

import com.mahaveer.myHelpDesk.dto.TicketRequestDto;
import com.mahaveer.myHelpDesk.model.Conversation;
import com.mahaveer.myHelpDesk.model.Ticket;
import com.mahaveer.myHelpDesk.repository.ConversationRepository;
import com.mahaveer.myHelpDesk.repository.TicketRepository;
import com.mahaveer.myHelpDesk.service.GeminiService;
import com.mahaveer.myHelpDesk.service.TicketService;
import com.mahaveer.myHelpDesk.util.CategoryPriorityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final ConversationRepository conversationRepository;
    private final GeminiService geminiService;

    public TicketServiceImpl(
            TicketRepository ticketRepository,
            ConversationRepository conversationRepository,
            GeminiService geminiService
    ) {
        this.ticketRepository = ticketRepository;
        this.conversationRepository = conversationRepository;
        this.geminiService = geminiService;
    }

    @Override
    public Ticket createTicket(TicketRequestDto dto) {

        // 1️⃣ Category & Priority
        String category = CategoryPriorityUtil.getCategory(dto.getQuery());
        String priority = CategoryPriorityUtil.getPriority(dto.getQuery());

        // 2️⃣ AI Response
        String aiResponse = geminiService.getAIResponse(dto.getQuery());

        // 3️⃣ Save Ticket
        Ticket ticket = new Ticket();
        ticket.setUserName(dto.getUserName());
        ticket.setUserQuery(dto.getQuery());
        ticket.setCategory(category);
        ticket.setPriority(priority);
        ticket.setAiResponse(aiResponse);
        ticket.setOfflineMode(aiResponse.startsWith("OFFLINE"));

        Ticket savedTicket = ticketRepository.save(ticket);

        // 4️⃣ Save USER Conversation
        Conversation userConversation = new Conversation();
        userConversation.setTicketId(savedTicket.getId());
        userConversation.setMessage(dto.getQuery());
        userConversation.setSender("USER");
        userConversation.setCreatedAt(LocalDateTime.now());

        conversationRepository.save(userConversation);

        // 5️⃣ Save AI Conversation
        Conversation aiConversation = new Conversation();
        aiConversation.setTicketId(savedTicket.getId());
        aiConversation.setMessage(aiResponse);
        aiConversation.setSender("AI");
        aiConversation.setCreatedAt(LocalDateTime.now());

        conversationRepository.save(aiConversation);

        return savedTicket;
    }
}
